package pl.fdaApi.restfulApi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fdaApi.restfulApi.exception.BadRequestException;
import pl.fdaApi.restfulApi.exception.RecordNotFoundException;
import pl.fdaApi.restfulApi.model.enitity.DrugRecord;
import pl.fdaApi.restfulApi.repository.DrugRecordRepository;

import java.lang.reflect.Field;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DrugRecordService {
    private final DrugRecordRepository drugRecordRepository;

    public DrugRecord saveDrugRecord(DrugRecord drugRecord) {
        checkIfRecordComplete(drugRecord);
        drugRecord.getProductNumbers().forEach(productNumber -> productNumber.setDrugRecord(drugRecord));
        return drugRecordRepository.save(drugRecord);
    }

    public DrugRecord findDrugRecordById(String applicationNumber) {
        return drugRecordRepository.findById(applicationNumber).orElseThrow(
                () -> new RecordNotFoundException("No record found with this application number"));
    }

    public List<DrugRecord> getAllDrugsRecord() {
        if (drugRecordRepository.findAll().isEmpty()) {
            throw new RecordNotFoundException("No records stored in system");
        }
        return drugRecordRepository.findAll();
    }

    public void deleteDrugRecord(String applicationNumber) {
        DrugRecord drugRecord = drugRecordRepository.findById(applicationNumber).orElseThrow(
                () -> new RecordNotFoundException("No record stored with provided application number"));
        drugRecordRepository.deleteById(applicationNumber);
    }

    private void checkIfRecordComplete(DrugRecord drugRecord) {
        for (Field field : drugRecord.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.get(drugRecord) == null) {
                    throw new BadRequestException("Record which you try to save has parameters missing");
                }
            } catch (IllegalAccessException e) {
                throw new BadRequestException("Error while checking record fields");
            }
        }
    }
}

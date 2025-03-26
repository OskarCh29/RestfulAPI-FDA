package pl.fdaApi.restfulapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fdaApi.restfulapi.exception.RecordNotFoundException;
import pl.fdaApi.restfulapi.model.enitity.DrugRecord;
import pl.fdaApi.restfulapi.repository.DrugRecordRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DrugRecordService {
    private final DrugRecordRepository drugRecordRepository;

    public DrugRecord saveDrugRecord(DrugRecord drugRecord) {
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
        drugRecordRepository.findById(applicationNumber).orElseThrow(
                () -> new RecordNotFoundException("No record stored with provided application number"));
        drugRecordRepository.deleteById(applicationNumber);
    }
}

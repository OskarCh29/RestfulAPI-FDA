package pl.fda.restfulapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.fda.restfulapi.exception.RecordNotFoundException;
import pl.fda.restfulapi.model.enitity.DrugRecord;
import pl.fda.restfulapi.repository.DrugRecordRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class DrugRecordServiceTests {

    @Autowired
    private DrugRecordService drugRecordService;

    @Autowired
    private DrugRecordRepository drugRecordRepository;


    @BeforeEach
    public void setUpTest() {
        drugRecordRepository.deleteAll();
    }

    @Test
    void saveDrugRecord() {
        DrugRecord drugRecord = initTestRecord();

        DrugRecord savedRecord = drugRecordService.saveDrugRecord(drugRecord);

        assertNotNull(savedRecord, "Record should not be null");
        assertEquals(drugRecord.getApplicationNumber(), savedRecord.getApplicationNumber());
        assertEquals(drugRecord.getManufacturerName(), savedRecord.getManufacturerName());
        assertEquals(drugRecord.getSubstanceName(), savedRecord.getSubstanceName());
        assertEquals(drugRecord.getProductNumber(), savedRecord.getProductNumber());

    }

    @Test
    void findDrugRecordById() {
        DrugRecord drugRecord = initTestRecord();

        DrugRecord foundRecord = drugRecordService.findDrugRecordById(drugRecord.getApplicationNumber());

        assertNotNull(foundRecord, "Found record should not be null");
        assertEquals(drugRecord.getApplicationNumber(), foundRecord.getApplicationNumber());
        assertEquals(drugRecord.getManufacturerName(), foundRecord.getManufacturerName());
        assertEquals(drugRecord.getSubstanceName(), foundRecord.getSubstanceName());
        assertEquals(drugRecord.getProductNumber(), foundRecord.getProductNumber());
    }

    @Test
    void findDrugRecordById_NotFound() {
        String nonExistingId = "WrongApplicationId";
        assertThrows(RecordNotFoundException.class, () -> {
            drugRecordService.findDrugRecordById(nonExistingId);
        }, "DataBase empty so any record should be found");

    }

    @Test
    void getAllRecords() {
        DrugRecord drugRecord = initTestRecord();

        List<DrugRecord> records = drugRecordService.getAllDrugsRecord();

        assertNotNull(records, "Should not be null. One record exists");
        assertEquals(1, records.size(), "Should have one record");

        assertEquals(drugRecord.getApplicationNumber(), records.getFirst().getApplicationNumber());
        assertEquals(drugRecord.getManufacturerName(), records.getFirst().getManufacturerName());
        assertEquals(drugRecord.getSubstanceName(), records.getFirst().getSubstanceName());
    }

    @Test
    void getAllRecord_noRecordsStored() {
        assertThrows(RecordNotFoundException.class, () -> {
            drugRecordService.getAllDrugsRecord();
        }, "No records stored. Should throw exception");
    }

    @Test
    void deleteById() {
        DrugRecord drugRecord = initTestRecord();

        drugRecordService.deleteDrugRecord(drugRecord.getApplicationNumber());

        Optional<DrugRecord> deletedRecord = drugRecordRepository.findById(drugRecord.getApplicationNumber());
        assertTrue(deletedRecord.isEmpty(), "Record should not be found");
    }

    @Test
    void deleteById_recordNotFound() {
        assertThrows(RecordNotFoundException.class, () -> {
            drugRecordService.deleteDrugRecord("NotExistingNumber");
        }, "Should throw exception. Cannot delete something that does not exist");
    }

    private DrugRecord initTestRecord() {
        DrugRecord testRecord = new DrugRecord();
        testRecord.setApplicationNumber("12345");
        testRecord.setManufacturerName("TestManufacturer");
        testRecord.setSubstanceName("TestSubstance");
        testRecord.setProductNumber("001");

        return drugRecordRepository.save(testRecord);
    }
}

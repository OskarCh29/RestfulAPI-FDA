package pl.fdaApi.restfulApi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import pl.fdaApi.restfulApi.model.enitity.DrugRecord;
import pl.fdaApi.restfulApi.model.enitity.ProductNumber;
import pl.fdaApi.restfulApi.repository.DrugRecordRepository;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = DrugRecordService.class)
class DrugRecordServiceTests {

    @Autowired
    private DrugRecordRepository drugRecordRepository;

    @Autowired
    private DrugRecordService drugRecordService;

    @Test
    void loadContext(){

    }

//    @BeforeEach
//    public void setUpTest() {
//        drugRecordRepository.deleteAll();
//    }
//
//    //    @Test
//    void SaveDrugRecord(){
//
//    }
//    @Test
//    void findDrugRecordById() {
//        DrugRecord drugRecord = initTestRecord();
//
//        DrugRecord foundRecord = drugRecordService.findDrugRecordById(drugRecord.getApplicationNumber());
//
//        assertNotNull(foundRecord, "Found record should not be null");
//        assertEquals(drugRecord.getApplicationNumber(), foundRecord.getApplicationNumber());
//        assertEquals(drugRecord.getManufacturerName(), foundRecord.getManufacturerName());
//        assertEquals(drugRecord.getSubstanceName(), foundRecord.getSubstanceName());
//        assertIterableEquals(drugRecord.getProductNumbers(), foundRecord.getProductNumbers());
//    }
//    @Test
//    void findDrugRecordById_NotFound(){
//
//    }
//    @Test
//    void getAllRecords(){
//
//    }
//    @Test
//    void getAllRecord_noRecordsStored(){
//
//    }
//    @Test
//    void deleteById(){
//
//    }
//    @Test
//    void deleteById_recordNotFound(){
//
//    }
//    @Test
//    void checkIfRecordComplete(){
//
//    }

//    private DrugRecord initTestRecord() {
//        DrugRecord testRecord = new DrugRecord();
//        testRecord.setApplicationNumber("12345");
//        testRecord.setManufacturerName("TestManufacturer");
//        testRecord.setSubstanceName("TestSubstance");
//        ProductNumber testNumber = new ProductNumber();
//        testNumber.setProductNumber("P123");
//        testNumber.setDrugRecord(testRecord);
//        return drugRecordRepository.save(testRecord);
//    }

}

package pl.fdaApi.restfulApi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pl.fdaApi.restfulApi.exception.RecordNotFoundException;
import pl.fdaApi.restfulApi.model.enitity.DrugRecord;
import pl.fdaApi.restfulApi.service.DrugRecordService;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DrugRecordController.class)
@ExtendWith(SpringExtension.class)
public class DrugRecordControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DrugRecordService drugRecordService;

    @Test
    void getDrugRecord() throws Exception {
        DrugRecord testRecord = new DrugRecord();
        String applicationNumber = "123";
        testRecord.setApplicationNumber(applicationNumber);

        when(drugRecordService.findDrugRecordById(anyString())).thenReturn(testRecord);

        mockMvc.perform(get("/drug/{applicationNumber}", applicationNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.applicationNumber").value(applicationNumber));
        verify(drugRecordService).findDrugRecordById(anyString());
    }

    @Test
    void getDrugRecord_notFound() throws Exception {
        String applicationNumber = "123";

        doThrow(new RecordNotFoundException("Record not found"))
                .when(drugRecordService).findDrugRecordById(anyString());

        mockMvc.perform(get("/drug/{applicationNumber}", applicationNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Record not found"));
    }

    @Test
    void deleteRecord() throws Exception {
        String applicationNumber = "123";
        String responseMessage = "Drug record with application number: 123 deleted";

        doNothing().when(drugRecordService).deleteDrugRecord(applicationNumber);

        mockMvc.perform(delete("/drug/{applicationNumber}", applicationNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.generalMessage").value(responseMessage));
    }

    @Test
    void getAllDrugRecords() throws Exception {
        DrugRecord firstRecord = new DrugRecord();
        DrugRecord secondRecord = new DrugRecord();
        firstRecord.setApplicationNumber("123");
        secondRecord.setApplicationNumber("345");
        List<DrugRecord> drugRecords = List.of(firstRecord, secondRecord);

        when(drugRecordService.getAllDrugsRecord()).thenReturn(drugRecords);

        mockMvc.perform(get("/drug")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].applicationNumber").value("123"))
                .andExpect(jsonPath("$[1].applicationNumber").value("345"));
        verify(drugRecordService).getAllDrugsRecord();
    }

    @Test
    void postNewDrugRecord() throws Exception {
        DrugRecord drugRecord = new DrugRecord();
        drugRecord.setApplicationNumber("123");
        drugRecord.setManufacturerName("Manufacturer");
        drugRecord.setSubstanceName("Substance");
        drugRecord.setProductNumber("001");

        when(drugRecordService.saveDrugRecord(any(DrugRecord.class))).thenReturn(drugRecord);

        mockMvc.perform(post("/drug")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(drugRecord)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.applicationNumber").value("123"))
                .andExpect(jsonPath("$.manufacturerName").value("Manufacturer"))
                .andExpect(jsonPath("$.substanceName").value("Substance"));
        verify(drugRecordService).saveDrugRecord(drugRecord);
    }

    @Test
    void postNewDrugRecord_notValidated() throws Exception {
        DrugRecord missingFieldRecord = new DrugRecord();
        missingFieldRecord.setApplicationNumber("123");

        mockMvc.perform(post("/drug")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(missingFieldRecord)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("was not provided")));
    }
}

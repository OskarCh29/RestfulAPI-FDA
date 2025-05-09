package pl.fda.restfulapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.fda.restfulapi.model.enitity.DrugRecord;
import pl.fda.restfulapi.model.response.GenericResponse;
import pl.fda.restfulapi.service.DrugRecordService;

import java.util.List;

@RestController
@RequestMapping("/drug")
@RequiredArgsConstructor
public class DrugRecordController {
    private final DrugRecordService drugRecordService;

    @GetMapping("/{applicationNumber}")
    public ResponseEntity<DrugRecord> getDrugRecord(@Valid @PathVariable String applicationNumber) {
        DrugRecord drugRecord = drugRecordService.findDrugRecordById(applicationNumber);
        return ResponseEntity.ok(drugRecord);
    }

    @DeleteMapping("{applicationNumber}")
    public ResponseEntity<GenericResponse> deleteDrugRecord(@Valid @PathVariable String applicationNumber) {
        drugRecordService.deleteDrugRecord(applicationNumber);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new GenericResponse("Drug record with application number: "
                        + applicationNumber + " deleted"));
    }

    @GetMapping
    public ResponseEntity<List<DrugRecord>> getAllDrugRecord() {
        return ResponseEntity.ok(drugRecordService.getAllDrugsRecord());
    }

    @PostMapping
    public ResponseEntity<DrugRecord> createDrugRecord(@Valid @RequestBody DrugRecord drugRecord) {
        DrugRecord savedRecord = drugRecordService.saveDrugRecord(drugRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecord);
    }
}

package mrp_simulator.api.controller;

import jakarta.validation.Valid;
import mrp_simulator.api.dtos.inforecord.CreateInfoRecordDTO;
import mrp_simulator.api.models.InfoRecord;
import mrp_simulator.api.services.inforecord.CreateAInfoRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inforecord")
public class CreateAInfoRecordController {

    @Autowired
    CreateAInfoRecordService createAInfoRecordService;

    @PostMapping
    public ResponseEntity<InfoRecord> createAInfoRecord(@RequestBody @Valid CreateInfoRecordDTO createInfoRecordDTO){
        return createAInfoRecordService.createAInfoRecord(createInfoRecordDTO);
    }

    @GetMapping("/inforecord")
    public ResponseEntity<List<InfoRecord>> getAllInfoRecord(){
        return createAInfoRecordService.getAllInfoRecords();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getInfoRecordById(@PathVariable(value = "id") Long idInfoRecord){
        return createAInfoRecordService.getInfoRecordById(idInfoRecord);
    }
}

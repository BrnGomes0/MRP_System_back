package mrp_simulator.api.controller;

import jakarta.validation.Valid;
import mrp_simulator.api.dtos.CreateInfoRecordDTO;
import mrp_simulator.api.models.CreateAInfoRecord;
import mrp_simulator.api.services.CreateAInfoRecordService;
import mrp_simulator.api.services.RegisterAItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CreateAInfoRecordController {

    @Autowired
    CreateAInfoRecordService createAInfoRecordService;

    @PostMapping("/create_inforecord")
    public ResponseEntity<CreateAInfoRecord> createAInfoRecord(@RequestBody @Valid CreateInfoRecordDTO createInfoRecordDTO){
        return createAInfoRecordService.createAInfoRecord(createInfoRecordDTO);
    }

    @GetMapping("/get_all_inforecord")
    public ResponseEntity<List<CreateAInfoRecord>> getAllInfoRecord(){
        return createAInfoRecordService.getAllInfoRecords();
    }

    @GetMapping("/get_inforecord_byId/{id}")
    public ResponseEntity<Object> getInfoRecordById(@PathVariable(value = "id") Long idInfoRecord){
        return createAInfoRecordService.getInfoRecordById(idInfoRecord);
    }
}

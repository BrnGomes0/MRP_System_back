package mrp_simulator.api.controller;

import jakarta.validation.Valid;
import mrp_simulator.api.dtos.inforecord.DTOCreateInfoRecord;
import mrp_simulator.api.dtos.inforecord.DTODeleteInforecord;
import mrp_simulator.api.models.InfoRecord;
import mrp_simulator.api.services.inforecord.InforecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/inforecord")
public class InforecordController {

    @Autowired
    InforecordService inforecordService;

    @PostMapping("/register")
    public ResponseEntity<InfoRecord> createAInfoRecord(@RequestBody @Valid DTOCreateInfoRecord createInfoRecordDTO){
        return inforecordService.createAInfoRecord(createInfoRecordDTO);
    }

    @GetMapping("/inforecord")
    public ResponseEntity<List<InfoRecord>> getAllInfoRecord(){
        return inforecordService.getAllInfoRecords();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getInfoRecordById(@PathVariable(value = "id") Long idInfoRecord){
        return inforecordService.getInfoRecordById(idInfoRecord);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DTODeleteInforecord> deleteInfoRecordById(@PathVariable(value = "id") Long idInfoRecord){
        var inforecord_deleted =  inforecordService.deleteInfoRecordById(idInfoRecord);
        return ResponseEntity.status(HttpStatus.OK).body(inforecord_deleted);
    }

}

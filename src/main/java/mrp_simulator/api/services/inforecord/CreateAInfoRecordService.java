package mrp_simulator.api.services.inforecord;

import mrp_simulator.api.dtos.inforecord.CreateInfoRecordDTO;
import mrp_simulator.api.models.InfoRecord;
import mrp_simulator.api.repositories.InforecordRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreateAInfoRecordService {

    @Autowired
    InforecordRepository createAInfoRecordRepository;

    public ResponseEntity<InfoRecord> createAInfoRecord(CreateInfoRecordDTO createInfoRecordDTO){
            var createAInfoRecordN = new InfoRecord();
            BeanUtils.copyProperties(createInfoRecordDTO, createAInfoRecordN);
            var createAInfoRecordF = createAInfoRecordRepository.save(createAInfoRecordN);
            return ResponseEntity.status(HttpStatus.CREATED).body(createAInfoRecordF);
    }

    public ResponseEntity<List<InfoRecord>> getAllInfoRecords(){
            return ResponseEntity.status(HttpStatus.OK).body(createAInfoRecordRepository.findAll());
    }

    public ResponseEntity<Object> getInfoRecordById(Long idInfoRecord){
            Optional<InfoRecord> infoRecordById = createAInfoRecordRepository.findById(idInfoRecord);
            if (!infoRecordById.isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("InfoRecord with ID: " + idInfoRecord + "not found!");
            }
            return ResponseEntity.status(HttpStatus.OK).body(infoRecordById.get());
    }
}
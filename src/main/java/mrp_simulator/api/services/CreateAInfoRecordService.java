package mrp_simulator.api.services;

import mrp_simulator.api.dtos.CreateInfoRecordDTO;
import mrp_simulator.api.models.CreateAInfoRecord;
import mrp_simulator.api.repositories.CreateAInfoRecordRepository;
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
    CreateAInfoRecordRepository createAInfoRecordRepository;

    public ResponseEntity<CreateAInfoRecord> createAInfoRecord(CreateInfoRecordDTO createInfoRecordDTO){
        try{
            var createAInfoRecordN = new CreateAInfoRecord();
            BeanUtils.copyProperties(createInfoRecordDTO, createAInfoRecordN);
            var createAInfoRecordF = createAInfoRecordRepository.save(createAInfoRecordN);
            return ResponseEntity.status(HttpStatus.CREATED).body(createAInfoRecordF);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    public ResponseEntity<List<CreateAInfoRecord>> getAllInfoRecords(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(createAInfoRecordRepository.findAll());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    public ResponseEntity<Object> getInfoRecordById(Long idInfoRecord){
        try{
            Optional<CreateAInfoRecord> infoRecordById = createAInfoRecordRepository.findById(idInfoRecord);
            if (infoRecordById.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Info Record de ID: " + idInfoRecord + " não encontrado!");
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(infoRecordById.get());
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passe o valor correto do ID!");
        }
    }
}
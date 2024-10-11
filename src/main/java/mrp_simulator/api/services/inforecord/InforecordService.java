package mrp_simulator.api.services.inforecord;

import jakarta.persistence.EntityNotFoundException;

import mrp_simulator.api.dtos.inforecord.DTOCreateInfoRecord;
import mrp_simulator.api.models.InfoRecord;
import mrp_simulator.api.models.Material;
import mrp_simulator.api.repositories.InforecordRepository;
import mrp_simulator.api.repositories.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InforecordService {

    @Autowired
    InforecordRepository InforecordRepository;

    @Autowired
    MaterialRepository materialRepository;

    public ResponseEntity<InfoRecord> createAInfoRecord(DTOCreateInfoRecord createInfoRecordDTO){
        try{
            Optional<Material> lastItemRegistered = materialRepository.findFirstByOrderByIdMaterialDesc();
            if(lastItemRegistered.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            Material last_material  = lastItemRegistered.get();

            InfoRecord createAInfoRecordN = new InfoRecord();
            var materialCode = last_material.getMaterialCode();
            createAInfoRecordN.setMaterialCode(materialCode);
            if(materialCode == 1230){
                createAInfoRecordN.setMaterialText("Material A");
            } else if (materialCode == 1240) {
                createAInfoRecordN.setMaterialText("Material B");
            }
            createAInfoRecordN.setPrice(createInfoRecordDTO.price());
            createAInfoRecordN.setLeadTime(createInfoRecordDTO.leadTime());

            if(materialCode == 1230) {
                createAInfoRecordN.setSupplierCode(929028);
            } else if (materialCode == 1240) {
                createAInfoRecordN.setSupplierCode(929029);
            }

            var createAInfoRecordF = InforecordRepository.save(createAInfoRecordN);
            return ResponseEntity.status(HttpStatus.CREATED).body(createAInfoRecordF);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    public ResponseEntity<List<InfoRecord>> getAllInfoRecords(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(InforecordRepository.findAll());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    public ResponseEntity<Object> getInfoRecordById(Long idInfoRecord){
        try{
            Optional<InfoRecord> infoRecordById = InforecordRepository.findById(idInfoRecord);
            if (infoRecordById.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Info Record de ID: " + idInfoRecord + " não encontrado!");
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(infoRecordById.get());
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passe o valor correto do ID!");
        }
    }

    public ResponseEntity<Object> deleteInfoRecordById(Long idInfoRecord){
        InfoRecord infoRecordById = InforecordRepository.findById(idInfoRecord)
                .orElseThrow(() -> new EntityNotFoundException("InfoRecord de ID: " + idInfoRecord + " inexistente!"));
        InforecordRepository.delete(infoRecordById);
        return ResponseEntity.status(HttpStatus.OK).body("InfoRecord de ID: " + idInfoRecord + " deletado com sucesso!");
    }
}
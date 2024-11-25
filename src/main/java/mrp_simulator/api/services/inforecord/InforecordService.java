package mrp_simulator.api.services.inforecord;

import jakarta.persistence.EntityNotFoundException;
import mrp_simulator.api.dtos.inforecord.DTOCreateInfoRecord;
import mrp_simulator.api.dtos.inforecord.DTODeleteInforecord;
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


            Optional<Material> lastItemRegistered = materialRepository.findFirstByOrderByIdMaterialDesc();
            if(lastItemRegistered.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            Material last_material  = lastItemRegistered.get();

            InfoRecord createAInfoRecordN = new InfoRecord();
            var materialCode = last_material.getMaterialCode();
            createAInfoRecordN.setMaterialCode(materialCode);
            if(materialCode.equals("1230")){
                createAInfoRecordN.setMaterialText("Material A");
            }
            createAInfoRecordN.setPrice(createInfoRecordDTO.price());
            createAInfoRecordN.setLeadTime(createInfoRecordDTO.leadTime());

            if(materialCode.equals("1230")) {
                createAInfoRecordN.setSupplierCode(929028);
            }

            var createAInfoRecordF = InforecordRepository.save(createAInfoRecordN);
            return ResponseEntity.status(HttpStatus.CREATED).body(createAInfoRecordF);

    }

    public ResponseEntity<List<InfoRecord>> getAllInfoRecords(){
            return ResponseEntity.status(HttpStatus.OK).body(InforecordRepository.findAll());
    }

    public ResponseEntity<Object> getInfoRecordById(Long idInfoRecord){
            Optional<InfoRecord> infoRecordById = InforecordRepository.findById(idInfoRecord);
            if (infoRecordById.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID InfoRecord: " + idInfoRecord + " not found!");
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(infoRecordById.get());
            }
    }

    public DTODeleteInforecord deleteInfoRecordById(Long idInfoRecord){
        InfoRecord infoRecordById = InforecordRepository.findById(idInfoRecord)
                .orElseThrow(() -> new EntityNotFoundException("ID InfoRecord: " + idInfoRecord + " non-existent!"));
        InforecordRepository.delete(infoRecordById);

        String message_deleted = "ID InfoRecord: " + idInfoRecord + "deleted successfully!";

        return new DTODeleteInforecord(idInfoRecord, message_deleted);
    }
}
package mrp_simulator.api.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import mrp_simulator.api.dtos.CreateInfoRecordDTO;
import mrp_simulator.api.models.CreateAInfoRecord;
import mrp_simulator.api.models.RegisterAItem;
import mrp_simulator.api.repositories.CreateAInfoRecordRepository;
import mrp_simulator.api.repositories.RegisterAItemRepository;
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

    @Autowired
    RegisterAItemRepository registerAItemRepository;

    public ResponseEntity<CreateAInfoRecord> createAInfoRecord(CreateInfoRecordDTO createInfoRecordDTO){
        try{
            Optional<RegisterAItem> lastItemRegistered = registerAItemRepository.findFirstByOrderByIdMaterialDesc();
            if(lastItemRegistered.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            RegisterAItem lastRegisterItem = lastItemRegistered.get();

            CreateAInfoRecord createAInfoRecordN = new CreateAInfoRecord();
            var materialCode = lastRegisterItem.getMaterialCode();
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

    public ResponseEntity<Object> deleteInfoRecordById(Long idInfoRecord){
        CreateAInfoRecord infoRecordById = createAInfoRecordRepository.findById(idInfoRecord)
                .orElseThrow(() -> new EntityNotFoundException("InfoRecord de ID: " + idInfoRecord + " inexistente!"));
        createAInfoRecordRepository.delete(infoRecordById);
        return ResponseEntity.status(HttpStatus.OK).body("InfoRecord de ID: " + idInfoRecord + " deletado com sucesso!");
    }
}
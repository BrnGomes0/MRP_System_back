package mrp_simulator.api.services.material;

import mrp_simulator.api.dtos.material.DTODeleteMaterial;
import mrp_simulator.api.dtos.material.DTODetailMaterialCreated;
import mrp_simulator.api.dtos.material.DTORegisterItem;
import mrp_simulator.api.models.Material;
import mrp_simulator.api.repositories.MaterialRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialService {

    @Autowired
    MaterialRepository registerAItemRepository;

    public DTODetailMaterialCreated createMaterial(DTORegisterItem registerAItemRecordDto){
            Material material = new Material(registerAItemRecordDto);
            registerAItemRepository.save(material);
            return new DTODetailMaterialCreated(
                    material.getIdMaterial(),
                    material.getMaterialCode(),
                    material.getDemand(),
                    material.getInitialInventory(),
                    material.getSafetyStock());
    }

    public ResponseEntity<List<Material>> getAllRegisteredItem(){
            return ResponseEntity.status(HttpStatus.OK).body(registerAItemRepository.findAll());
    }

    public ResponseEntity<Object> getRegisteredItemById(Long idMaterial){
            Optional<Material> getItemById = registerAItemRepository.findById(idMaterial);
            if(getItemById.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID Material: " + idMaterial + " not found!");
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(getItemById.get());
            }

    }

    public DTODeleteMaterial deleteRegisteredItemById(Long idMaterial){
        Material registeredItemById = registerAItemRepository.findById(idMaterial)
                .orElseThrow(() -> new EntityNotFoundException("ID Material: " + idMaterial + " non-existent!"));
        registerAItemRepository.delete(registeredItemById);

        String messsage_deleted = "ID Material: " + idMaterial + "deleted successfully!";

        return new DTODeleteMaterial(idMaterial, messsage_deleted);
    }
}
package mrp_simulator.api.services.material;

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

    public ResponseEntity<Material> registerAItem(DTORegisterItem registerAItemRecordDto){
        try{
            var registerAItemn = new Material();
            BeanUtils.copyProperties(registerAItemRecordDto, registerAItemn);
            var registerANewItem = registerAItemRepository.save(registerAItemn);
            return ResponseEntity.status(HttpStatus.CREATED).body(registerANewItem);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    public ResponseEntity<List<Material>> getAllRegisteredItem(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(registerAItemRepository.findAll());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    public ResponseEntity<Object> getRegisteredItemById(Long idMaterial){
        try{
            Optional<Material> getItemById = registerAItemRepository.findById(idMaterial);
            if(getItemById.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item de ID: " + idMaterial + " não encontrado!");
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(getItemById.get());
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao pegar o item deste ID");
        }

    }

    public ResponseEntity<Object> deleteRegisteredItemById(Long idMaterial){
        Material registeredItemById = registerAItemRepository.findById(idMaterial)
                .orElseThrow(() -> new EntityNotFoundException("InfoRecord de ID: " + idMaterial + " inexistente!"));
        registerAItemRepository.delete(registeredItemById);
        return ResponseEntity.status(HttpStatus.OK).body("InfoRecord de ID: " + idMaterial + " deletado com sucesso!");
    }

}

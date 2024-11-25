package mrp_simulator.api.services.material;

import mrp_simulator.api.dtos.auth.DTOAuth;
import mrp_simulator.api.dtos.material.DTODetailMaterialCreated;
import mrp_simulator.api.dtos.material.DTORegisterItem;
import mrp_simulator.api.infra.error.exceptions.MaterialRegistered;
import mrp_simulator.api.models.Material;
import mrp_simulator.api.repositories.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {

    @Autowired
    MaterialRepository registerAItemRepository;

    public DTODetailMaterialCreated createMaterial(DTORegisterItem registerAItemRecordDto, @AuthenticationPrincipal Jwt jwt){

            DTOAuth user = new DTOAuth(jwt);
            Material material = new Material(registerAItemRecordDto);
            material.setUsername(user.userName());

            boolean isMaterialRegistered = registerAItemRepository.existsByUsername(user.userName());
            if(isMaterialRegistered){
                throw new MaterialRegistered("Material registered for that user! You can't create more material");
            }

            registerAItemRepository.save(material);
            return new DTODetailMaterialCreated(
                    material.getIdMaterial(),
                    material.getMaterialCode(),
                    material.getDemand(),
                    material.getInitialInventory(),
                    material.getSafetyStock(),
                    material.getUsername()
            );
    }

    public ResponseEntity<List<Material>> getAllRegisteredItem(){
        return ResponseEntity.status(HttpStatus.OK).body(registerAItemRepository.findAll());
    }
}
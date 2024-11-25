package mrp_simulator.api.controller;

import jakarta.validation.Valid;
import mrp_simulator.api.dtos.material.DTODetailMaterialCreated;
import mrp_simulator.api.dtos.material.DTORegisterItem;
import mrp_simulator.api.models.Material;
import mrp_simulator.api.services.material.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/material")
public class MaterialControlller {

    @Autowired
    MaterialService registerAItemService;
    
    @PostMapping
    public ResponseEntity<DTODetailMaterialCreated> registerAItem(@RequestBody @Valid DTORegisterItem registerAItemRecordDto, UriComponentsBuilder uriComponentsBuilder, @AuthenticationPrincipal Jwt jwt){
         var material = registerAItemService.createMaterial(registerAItemRecordDto, jwt);
         var uri = uriComponentsBuilder.path("/material/{material_id}").build(material.material_id());

         return ResponseEntity.created(uri).body(material);
    }

    @GetMapping("/materials")
    public ResponseEntity<List<Material>> getAllRegisteredItem(){
        return registerAItemService.getAllRegisteredItem();
    }

}

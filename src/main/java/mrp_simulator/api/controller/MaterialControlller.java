package mrp_simulator.api.controller;

import jakarta.validation.Valid;
import mrp_simulator.api.dtos.material.DTODeleteMaterial;
import mrp_simulator.api.dtos.material.DTORegisterItem;
import mrp_simulator.api.models.Material;
import mrp_simulator.api.services.material.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/material")
public class MaterialControlller {

    @Autowired
    MaterialService registerAItemService;

    @PostMapping
    public ResponseEntity<Material> registerAItem(@RequestBody @Valid DTORegisterItem registerAItemRecordDto){
        return registerAItemService.registerAItem(registerAItemRecordDto);
    }

    @GetMapping("/materials")
    public ResponseEntity<List<Material>> getAllRegisteredItem(){
        return registerAItemService.getAllRegisteredItem();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRegisteredItemById(@PathVariable(value = "id") Long idMaterial){
        return registerAItemService.getRegisteredItemById(idMaterial);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DTODeleteMaterial> deleteRegisteredItemById(@PathVariable(value = "id") Long idMaterial){
        var material_deleted =  registerAItemService.deleteRegisteredItemById(idMaterial);
        return ResponseEntity.status(HttpStatus.OK).body(material_deleted);
    }
}

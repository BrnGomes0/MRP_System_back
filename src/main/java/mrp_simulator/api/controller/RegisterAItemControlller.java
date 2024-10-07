package mrp_simulator.api.controller;

import jakarta.validation.Valid;
import mrp_simulator.api.dtos.material.RegisterAItemRecordDto;
import mrp_simulator.api.models.Material;
import mrp_simulator.api.services.material.RegisterAItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/material")
public class RegisterAItemControlller {

    @Autowired
    RegisterAItemService registerAItemService;

    @PostMapping
    public ResponseEntity<Material> registerAItem(@RequestBody @Valid RegisterAItemRecordDto registerAItemRecordDto){
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

    @DeleteMapping("/delete_registered_item_by_id/{id}")
    public ResponseEntity<Object> deleteRegisteredItemById(@PathVariable(value = "id") Long idMaterial){
        return registerAItemService.deleteRegisteredItemById(idMaterial);
    }
}

package mrp_simulator.api.controller;

import jakarta.validation.Valid;
import mrp_simulator.api.dtos.RegisterAItemRecordDto;
import mrp_simulator.api.models.RegisterAItem;
import mrp_simulator.api.repositories.RegisterAItemRepository;
import mrp_simulator.api.services.RegisterAItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RegisterAItemControlller {

    @Autowired
    RegisterAItemService registerAItemService;

    @PostMapping("/register_a_item")
    public ResponseEntity<RegisterAItem> registerAItem(@RequestBody @Valid RegisterAItemRecordDto registerAItemRecordDto){
        return registerAItemService.registerAItem(registerAItemRecordDto);
    }

    @GetMapping("/get_all_registered_item")
    public ResponseEntity<List<RegisterAItem>> getAllRegisteredItem(){
        return registerAItemService.getAllRegisteredItem();
    }

    @GetMapping("/get_registered_item_by_id/{id}")
    public ResponseEntity<Object> getRegisteredItemById(@PathVariable(value = "id") Long idMaterial){
        return registerAItemService.getRegisteredItemById(idMaterial);
    }
}

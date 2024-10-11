package mrp_simulator.api.controller;

import jakarta.transaction.Transactional;
import mrp_simulator.api.dtos.inventory.DTOAllInventory;
import mrp_simulator.api.dtos.inventory.DTODetailFirstWeek;
import mrp_simulator.api.services.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/register/{material_id}")
    public ResponseEntity<DTODetailFirstWeek> createFirstWeek(@PathVariable Long material_id, UriComponentsBuilder uriBuilder){
        var firstWeek = inventoryService.registerFirstWeek(material_id);
        var uri = uriBuilder.path("/inventory/{id}").build(firstWeek.id());
        return ResponseEntity.created(uri).body(firstWeek);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DTOAllInventory>> returnAllInventory(){
        var inventories = inventoryService.getAllInventories();
        return ResponseEntity.status(HttpStatus.OK).body(inventories);
    }
}

package mrp_simulator.api.controller;

import mrp_simulator.api.dtos.inventory.DTORegisterInventory;
import mrp_simulator.api.services.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // POST the first inventory
    @PostMapping("/{materialId}")
    public ResponseEntity<DTORegisterInventory> registerFirstInventory(@PathVariable Long materialId){
        var inventory = inventoryService.createInventory(materialId);
        return ResponseEntity.status(HttpStatus.OK).body(inventory);
    }
}
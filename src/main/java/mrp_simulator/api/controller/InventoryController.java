package mrp_simulator.api.controller;

import mrp_simulator.api.dtos.inventory.DTOAllInventory;
import mrp_simulator.api.dtos.inventory.DTODetailFirstWeek;
import mrp_simulator.api.services.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/register")
    public ResponseEntity<DTODetailFirstWeek> createFirstWeek(UriComponentsBuilder uriBuilder, @AuthenticationPrincipal Jwt jwt){
        var firstWeek = inventoryService.registerFirstWeek(jwt);
        var uri = uriBuilder.path("/inventory/{id}").build(firstWeek.id());
        return ResponseEntity.created(uri).body(firstWeek);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DTOAllInventory>> returnAllInventory(@AuthenticationPrincipal Jwt jwt){
        var inventories = inventoryService.getAllInventories(jwt);
        return ResponseEntity.status(HttpStatus.OK).body(inventories);
    }
    @GetMapping("/allWithoutJWT")
    public ResponseEntity<List<DTOAllInventory>> returnAllInventoryWithoutJWT(){
        var inventories = inventoryService.getAllInventoriesWithoutJWT();
        return ResponseEntity.status(HttpStatus.OK).body(inventories);
    }
}

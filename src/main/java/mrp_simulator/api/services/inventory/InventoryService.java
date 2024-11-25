package mrp_simulator.api.services.inventory;

import jakarta.transaction.Transactional;
import mrp_simulator.api.dtos.auth.DTOAuth;
import mrp_simulator.api.dtos.inventory.DTOAllInventory;
import mrp_simulator.api.dtos.inventory.DTODetailFirstWeek;
import mrp_simulator.api.infra.error.exceptions.FirstWeekInventoryRegistred;
import mrp_simulator.api.infra.error.exceptions.MaterialNotFound;
import mrp_simulator.api.models.Inventory;
import mrp_simulator.api.models.Material;
import mrp_simulator.api.repositories.InventoryRepository;
import mrp_simulator.api.repositories.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Transactional
    public DTODetailFirstWeek registerFirstWeek (@AuthenticationPrincipal Jwt jwt){

        DTOAuth user = new DTOAuth(jwt);

        Material material = materialRepository.findByUsername(user.userName())
                .orElseThrow(() -> new MaterialNotFound("Material Not Found for user: " + user.userName()));

        boolean inventoryExists = inventoryRepository.existsByWeekAndMaterial_Username(1, material.getUsername());
        if(inventoryExists){
            throw new FirstWeekInventoryRegistred("Inventory for Week 1 already exists for user: " + user.userName());
        }

        Inventory inventory = new Inventory();
        inventory.setInventory_id(1L);
        inventory.setWeek(1);
        inventory.setInitialInventory(material.getInitialInventory());
        inventory.setFinalInventory(material.getInitialInventory());
        inventory.setSafetyStock(material.getSafetyStock());
        inventory.setDemand(material.getDemand());
        inventory.setMaterial(material);

        if(material.getMaterialCode().equals("1230")){
            inventory.setMaterialName("Material A - (Pen)");
        }

        inventoryRepository.save(inventory);

        return new DTODetailFirstWeek(
                inventory.getInventory_id(),
                inventory.getWeek(),
                inventory.getMaterialName(),
                inventory.getSafetyStock(),
                inventory.getDemand(),
                inventory.getInitialInventory(),
                inventory.getFinalInventory(),
                inventory.getMaterial().getUsername()
        );
    }

    // GET ALL Material
    public List<DTOAllInventory> getAllInventories(@AuthenticationPrincipal Jwt jwt){

        DTOAuth user = new DTOAuth(jwt);

        List<Inventory> inventories = inventoryRepository.findByMaterialUsername(user.userName());

        // Convert each inventory of DTO and return a list
        return inventories.stream().map(
                inventory -> new DTOAllInventory(
                        inventory.getInventory_id(),
                        inventory.getWeek(),
                        inventory.getMaterialName(),
                        inventory.getDemand(),
                        inventory.getInitialInventory(),
                        inventory.getFinalInventory(),
                        inventory.getSafetyStock()
                )).collect(Collectors.toList());
    }
    // GET ALL Material
    public List<DTOAllInventory> getAllInventoriesWithoutJWT(){

        List<Inventory> inventories = inventoryRepository.findAll();

        // Convert each inventory of DTO and return a list
        return inventories.stream().map(
                inventory -> new DTOAllInventory(
                        inventory.getInventory_id(),
                        inventory.getWeek(),
                        inventory.getMaterialName(),
                        inventory.getDemand(),
                        inventory.getInitialInventory(),
                        inventory.getFinalInventory(),
                        inventory.getSafetyStock()
                )).collect(Collectors.toList());
    }
}
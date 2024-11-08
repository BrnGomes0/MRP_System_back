package mrp_simulator.api.services.inventory;

import jakarta.transaction.Transactional;
import mrp_simulator.api.dtos.inventory.DTOAllInventory;
import mrp_simulator.api.dtos.inventory.DTODetailFirstWeek;
import mrp_simulator.api.infra.error.exceptions.*;
import mrp_simulator.api.models.Inventory;
import mrp_simulator.api.models.Material;
import mrp_simulator.api.models.PurchaseOrder;
import mrp_simulator.api.repositories.InventoryRepository;
import mrp_simulator.api.repositories.MaterialRepository;
import mrp_simulator.api.repositories.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Transactional
    // POST -> First Week (1)
    public DTODetailFirstWeek registerFirstWeek (@PathVariable Long material_id){

        // Search Material by ID
        Material material = materialRepository.findById(material_id).
                orElseThrow(() -> new MaterialNotFound("Material Not Found with that ID: " + material_id));

        boolean isMaterialRegistered = inventoryRepository.existsByMaterialAndWeek(material, 0);
        if(isMaterialRegistered){
            throw new FirstWeekMaterialRegisted("Material already registered for the first week: " + material.getMaterialCode());
        }

        int initialInventory = material.getInitialInventory();
        int finalInventory = initialInventory - material.getDemand();
        int orderPlaced = material.getSafetyStock() - finalInventory;

        Inventory inventory = new Inventory();
        inventory.setWeek(0);
        inventory.setInitialInventory(initialInventory);
        inventory.setFinalInventory(finalInventory);
        inventory.setSafetyStock(material.getSafetyStock());
        inventory.setDemand(material.getDemand());
        inventory.setMaterial(material);
        if(material.getMaterialCode().equals("1230")){
            inventory.setMaterialName("Material A - (Pen)");
        } else if (material.getMaterialCode().equals("1240")) {
            inventory.setMaterialName("Material B - (Package)");
        }

        inventoryRepository.save(inventory);

        return new DTODetailFirstWeek(
                inventory.getInventory_id(),
                inventory.getWeek(),
                inventory.getMaterialName(),
                inventory.getSafetyStock(),
                inventory.getDemand(),
                inventory.getInitialInventory(),
                inventory.getFinalInventory()
        );
    }

    // GET ALL Material
    public List<DTOAllInventory> getAllInventories(){
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

    // GET ALL with filter Materials A
    public List<DTOAllInventory> getAllInventoryBasedMaterialA(){

        List<Inventory> inventories = inventoryRepository.findAll();

        return inventories.stream()
                .filter(inventory -> "Material A - (Pen)".equals(inventory.getMaterialName()))
                .map(inventory -> new DTOAllInventory(
                        inventory.getInventory_id(),
                        inventory.getWeek(),
                        inventory.getMaterialName(),
                        inventory.getDemand(),
                        inventory.getInitialInventory(),
                        inventory.getFinalInventory(),
                        inventory.getSafetyStock()
                )).collect(Collectors.toList());

    }
    // GET ALL with filter Materials B
    public List<DTOAllInventory> getAllInventoryBasedMaterialB(){

        List<Inventory> inventories = inventoryRepository.findAll();

        return inventories.stream()
                .filter(inventory -> "Material B - (Package)".equals(inventory.getMaterialName()))
                .map(inventory -> new DTOAllInventory(
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
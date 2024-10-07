package mrp_simulator.api.services.inventory;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import mrp_simulator.api.dtos.inventory.DTORegisterInventory;
import mrp_simulator.api.dtos.inventory.DTODetailInventory;
import mrp_simulator.api.dtos.inventory.DTOUpdateInventory;
import mrp_simulator.api.models.Inventory;
import mrp_simulator.api.repositories.InventoryRepository;
import mrp_simulator.api.repositories.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private MaterialRepository materialRepository;

    // POST
    @Transactional
    public DTORegisterInventory createInventory(@PathVariable Long materialId){

        // Search Material
        var material = materialRepository.findById(materialId).orElseThrow(() -> new RuntimeException("Material not found!"));

        Inventory inventory = new Inventory();

        /*
            InicialInventory = Material InicialInventory
            SafetyStock = Material SafetyStock
            Consumption = InicialInventory - demand
        */
        inventory.setInicialInventory(material.getInicialInventory());
        inventory.setConsumption(material.getInicialInventory() - material.getDemand());
        inventory.setSafetyStock(material.getSafetyStock());
        inventory.setFinalInventory(material.getInicialInventory() - inventory.getConsumption());

        inventoryRepository.save(inventory);

        return new DTORegisterInventory(
                material.getIdMaterial(),
                1,
                inventory.getSafetyStock(),
                inventory.getConsumption(),
                inventory.getInicialInventory(),
                inventory.getFinalInventory()
        );
    }

    // UPDATE
    /*
        Fixed Fields:
            Safety Stock
            Initial Inventory
    */
//    @Transactional
//    public DTODetailInventory updateInventory(@RequestBody @Valid DTOUpdateInventory dtoUpdateInventory, @PathVariable Long inventory_id){
//
//        var inventory = materialRepository.findById(material_id).orElseThrow(() -> new RuntimeException("Material not found!"));
//
//        Inventory inventory = new Inventory(dtoUpdateInventory);
//        inventory.setConsumption(dtoUpdateInventory.comsumption());
//        inventory.setOrder_received(dtoUpdateInventory.order_received());
//        inventory.setFinalInventory(inventory.getFinalInventory() + dtoUpdateInventory.order_received());
//
//
//    }

    // GET ALL

}
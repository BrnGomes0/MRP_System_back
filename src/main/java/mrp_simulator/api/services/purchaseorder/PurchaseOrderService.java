package mrp_simulator.api.services.purchaseorder;

import jakarta.transaction.Transactional;
import mrp_simulator.api.dtos.purchase.DTOAllPurchaseOrder;
import mrp_simulator.api.dtos.purchase.DTODetailUpdatePurchasingOrder;
import mrp_simulator.api.dtos.purchase.DTODetailtFirstWeekPurchase;
import mrp_simulator.api.dtos.purchase.DTOUpdatePurchasingOrder;
import mrp_simulator.api.infra.error.exceptions.FirstWeekInventoryRegistred;
import mrp_simulator.api.infra.error.exceptions.InventoryNotFound;
import mrp_simulator.api.infra.error.exceptions.PurchasingOrderNotFound;
import mrp_simulator.api.models.Inventory;
import mrp_simulator.api.models.PurchaseOrder;
import mrp_simulator.api.repositories.InventoryRepository;
import mrp_simulator.api.repositories.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    // POST
    @Transactional
    public DTODetailtFirstWeekPurchase registerFirstPurchaseWeek(@PathVariable Long inventory_id){

        // Search Inventory by ID
        Inventory inventory = inventoryRepository.findById(inventory_id).orElseThrow(
                () -> new InventoryNotFound("Inventory Not Found with ID: " + inventory_id));

        boolean isInventoryRegistred = purchaseOrderRepository.existsByInventoriesContainingAndWeek(inventory, 1);
        if(isInventoryRegistred){
            throw new FirstWeekInventoryRegistred("Inventory already registered for the first week: " + inventory.getMaterialName());
        }

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setWeek(1);
        purchaseOrder.setDemand(inventory.getDemand());
        purchaseOrder.setOrderPlaced(0);
        purchaseOrder.setOrderReceived(0);
        purchaseOrder.addInventory(inventory);

        purchaseOrderRepository.save(purchaseOrder);

        return new DTODetailtFirstWeekPurchase(
                purchaseOrder.getPurchaseOrder_id(),
                purchaseOrder.getWeek(),
                inventory.getMaterialName(),
                purchaseOrder.getOrderPlaced(),
                purchaseOrder.getOrderReceived()
        );

    }

    // UPDATE
    public DTODetailUpdatePurchasingOrder updatePurchasingOrder(DTOUpdatePurchasingOrder dtoUpdatePurchasingOrder, Long inventory_id) {

        Inventory originalInventory = inventoryRepository.findById(inventory_id).orElseThrow(
                () -> new InventoryNotFound("Inventory Not Found with ID: " + inventory_id));


        PurchaseOrder lastPurchaseOrder = purchaseOrderRepository.findFirstByInventoriesContainingOrderByWeekDesc(originalInventory).orElseThrow(
                () -> new PurchasingOrderNotFound("Purchase order not found for inventory ID: " + inventory_id)
        );

        System.out.println("LONG Inventory ID: " + inventory_id);

        // Creating a logic in MRP (Variables)
        int initialInventory = originalInventory.getFinalInventory() + lastPurchaseOrder.getOrderReceived();
        int finalInventory = initialInventory - lastPurchaseOrder.getDemand() + lastPurchaseOrder.getOrderReceived();
        int orderPlaced = originalInventory.getSafetyStock() - finalInventory;


        Inventory newInventory = new Inventory();
        newInventory.setMaterialName(originalInventory.getMaterialName());
        newInventory.setMaterial(originalInventory.getMaterial());
        newInventory.setSafetyStock(originalInventory.getSafetyStock());
        newInventory.setInitialInventory(initialInventory);
        newInventory.setFinalInventory(finalInventory);
        newInventory.setPendingOrder(orderPlaced);
        newInventory.setDemand(dtoUpdatePurchasingOrder.demand());
        newInventory.setWeek(lastPurchaseOrder.getWeek() + 1);

        if(lastPurchaseOrder.getWeek() > 2){
            initialInventory = originalInventory.getFinalInventory() + dtoUpdatePurchasingOrder.orderReceived();
            finalInventory = initialInventory - dtoUpdatePurchasingOrder.demand() + dtoUpdatePurchasingOrder.orderReceived();
            orderPlaced = originalInventory.getSafetyStock() - finalInventory;

        }

        PurchaseOrder newPurchaseOrder = new PurchaseOrder();
        newPurchaseOrder.setWeek(lastPurchaseOrder.getWeek() + 1);
        newPurchaseOrder.setDemand(dtoUpdatePurchasingOrder.demand());
        newPurchaseOrder.setOrderPlaced(orderPlaced);
        newPurchaseOrder.setOrderReceived(dtoUpdatePurchasingOrder.orderReceived());

        newPurchaseOrder.getInventories().add(newInventory);

        newInventory.getRelatedPurchaseOrders().add(newPurchaseOrder);

        inventoryRepository.save(newInventory);


        purchaseOrderRepository.save(newPurchaseOrder);

        return new DTODetailUpdatePurchasingOrder(
                newPurchaseOrder.getPurchaseOrder_id(),
                newPurchaseOrder.getWeek(),
                newPurchaseOrder.getOrderReceived(),
                newPurchaseOrder.getOrderPlaced(),
                newInventory.getMaterialName()
        );
    }

    // GET ALL with filter Materials A
    public List<DTOAllPurchaseOrder> getAllPurchasingOrdersBasedMaterialA(){

        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();

        return purchaseOrders.stream()
                .filter(purchaseOrder -> purchaseOrder.getInventories() != null &&
                        purchaseOrder.getInventories().stream()
                                .anyMatch(inventory -> "Material A - (Pen)".equals(inventory.getMaterialName())))
                .map(purchaseOrder -> new DTOAllPurchaseOrder(
                        purchaseOrder.getPurchaseOrder_id(),
                        purchaseOrder.getWeek(),
                        purchaseOrder.getOrderPlaced(),
                        purchaseOrder.getOrderReceived(),
                        purchaseOrder.getDemand(),
                        purchaseOrder.getInventories().stream()
                                .filter(inventory -> "Material A - (Pen)".equals(inventory.getMaterialName()))
                                .findFirst()
                                .map(Inventory::getMaterialName)
                                .orElse(null)
                ))
                .collect(Collectors.toList());
    }

    // GET ALL with filter Materials B
    public List<DTOAllPurchaseOrder> getAllPurchasingOrdersBasedMaterialB(){

        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();

        return purchaseOrders.stream()
                .filter(purchaseOrder -> purchaseOrder.getInventories() != null &&
                        purchaseOrder.getInventories().stream()
                                .anyMatch(inventory -> "Material B - (Package)".equals(inventory.getMaterialName())))
                .map(purchaseOrder -> new DTOAllPurchaseOrder(
                        purchaseOrder.getPurchaseOrder_id(),
                        purchaseOrder.getWeek(),
                        purchaseOrder.getOrderPlaced(),
                        purchaseOrder.getOrderReceived(),
                        purchaseOrder.getDemand(),
                        purchaseOrder.getInventories().stream()
                                .filter(inventory -> "Material B - (Package)".equals(inventory.getMaterialName()))
                                .findFirst()
                                .map(Inventory::getMaterialName)
                                .orElse(null)
                ))
                .collect(Collectors.toList());
    }
}
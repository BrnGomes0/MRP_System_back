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

        boolean isInventoryRegistred = purchaseOrderRepository.existsByInventoryAndWeek(inventory, 1);
        if(isInventoryRegistred){
            throw new FirstWeekInventoryRegistred("Inventory already registered for the first week: " + inventory.getMaterialName());
        }

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setWeek(1);
        purchaseOrder.setDemand(inventory.getDemand());
        purchaseOrder.setOrderPlaced(0);
        purchaseOrder.setOrderReceived(0);
        purchaseOrder.setInventory(inventory);

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


        PurchaseOrder lastPurchaseOrder = purchaseOrderRepository.findFirstByInventoryOrderByWeekDesc(originalInventory).orElseThrow(
                () -> new PurchasingOrderNotFound("Purchase order not found for inventory ID: " + inventory_id)
        );


        // Calculate the orderPlaced
        int inventory = originalInventory.getQuantityInInventory();
        int demand = dtoUpdatePurchasingOrder.demand();
        int orderPlaced = demand > inventory ? demand - inventory: 0;

        PurchaseOrder newPurchaseOrder = new PurchaseOrder();
        newPurchaseOrder.setWeek(lastPurchaseOrder.getWeek() + 1);
        newPurchaseOrder.setDemand(dtoUpdatePurchasingOrder.demand());
        newPurchaseOrder.setOrderPlaced(orderPlaced);
        newPurchaseOrder.setOrderReceived(dtoUpdatePurchasingOrder.orderReceived());
        newPurchaseOrder.setInventory(originalInventory);


        Inventory newInventory = new Inventory();
        newInventory.setMaterialName(originalInventory.getMaterialName());
        newInventory.setMaterial(originalInventory.getMaterial());
        newInventory.setQuantityInInventory(originalInventory.getQuantityInInventory() + dtoUpdatePurchasingOrder.orderReceived());
        newInventory.setDemand(dtoUpdatePurchasingOrder.demand());
        newInventory.setWeek(newPurchaseOrder.getWeek());
        newInventory.setRelatedPurchaseOrder(newPurchaseOrder);


        purchaseOrderRepository.save(newPurchaseOrder);
        inventoryRepository.save(newInventory);


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
                .filter(purchaseOrder -> "Material A - (Pen)".equals(purchaseOrder.getInventory().getMaterialName()))
                .map(purchaseOrder -> new DTOAllPurchaseOrder(
                        purchaseOrder.getPurchaseOrder_id(),
                        purchaseOrder.getWeek(),
                        purchaseOrder.getOrderPlaced(),
                        purchaseOrder.getOrderReceived(),
                        purchaseOrder.getDemand(),
                        purchaseOrder.getInventory().getMaterialName()
                )).collect(Collectors.toList());
    }

    // GET ALL with filter Materials A
    public List<DTOAllPurchaseOrder> getAllPurchasingOrdersBasedMaterialB(){

        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();

        return purchaseOrders.stream()
                .filter(purchaseOrder -> "Material B - (Package)".equals(purchaseOrder.getInventory().getMaterialName()))
                .map(purchaseOrder -> new DTOAllPurchaseOrder(
                        purchaseOrder.getPurchaseOrder_id(),
                        purchaseOrder.getWeek(),
                        purchaseOrder.getOrderPlaced(),
                        purchaseOrder.getOrderReceived(),
                        purchaseOrder.getDemand(),
                        purchaseOrder.getInventory().getMaterialName()
                )).collect(Collectors.toList());
    }
}
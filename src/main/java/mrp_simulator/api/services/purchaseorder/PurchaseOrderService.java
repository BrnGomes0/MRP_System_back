package mrp_simulator.api.services.purchaseorder;

import jakarta.transaction.Transactional;
import mrp_simulator.api.dtos.auth.DTOAuth;
import mrp_simulator.api.dtos.purchase.DTOAllPurchaseOrder;
import mrp_simulator.api.dtos.purchase.DTODetailUpdatePurchasingOrder;
import mrp_simulator.api.dtos.purchase.DTODetailtFirstWeekPurchase;
import mrp_simulator.api.dtos.purchase.DTOUpdatePurchasingOrder;
import mrp_simulator.api.infra.error.exceptions.FirstWeekInventoryRegistred;
import mrp_simulator.api.infra.error.exceptions.InventoryNotFound;
import mrp_simulator.api.infra.error.exceptions.PurchasingOrderNotFound;
import mrp_simulator.api.models.Inventory;
import mrp_simulator.api.models.Material;
import mrp_simulator.api.models.PurchaseOrder;
import mrp_simulator.api.repositories.InventoryRepository;
import mrp_simulator.api.repositories.MaterialRepository;
import mrp_simulator.api.repositories.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private MaterialRepository materialRepository;

    // POST
    @Transactional
    public DTODetailtFirstWeekPurchase registerFirstPurchaseWeek(@AuthenticationPrincipal Jwt jwt){

        DTOAuth user = new DTOAuth(jwt);

        Inventory inventory = inventoryRepository.findFirstByMaterial_Username(user.userName())
                .orElseThrow(() -> new InventoryNotFound("Inventory not found or unauthorized access"));

        boolean isInventoryRegistred = purchaseOrderRepository.existsByInventoriesContainingAndWeekAndMaterial_Username(inventory, 1, user.userName());
        if(isInventoryRegistred){
            throw new FirstWeekInventoryRegistred("Inventory already registered for the first week for user: " + user.userName() + " - " + inventory.getMaterialName());
        }

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setWeek(1);
        purchaseOrder.setDemand(inventory.getDemand());
        purchaseOrder.setOrderPlaced(0);
        purchaseOrder.setOrderReceived(0);
        purchaseOrder.addInventory(inventory);
        purchaseOrder.setMaterial(inventory.getMaterial());

        purchaseOrderRepository.save(purchaseOrder);

        return new DTODetailtFirstWeekPurchase(
                purchaseOrder.getPurchaseOrder_id(),
                purchaseOrder.getWeek(),
                inventory.getMaterialName(),
                purchaseOrder.getOrderPlaced(),
                purchaseOrder.getOrderReceived()
        );

    }

    @Transactional
    public DTODetailUpdatePurchasingOrder updatePurchasingOrder(DTOUpdatePurchasingOrder dtoUpdatePurchasingOrder, @AuthenticationPrincipal Jwt jwt) {

        DTOAuth user = new DTOAuth(jwt);

        Material material = materialRepository.findByUsername(user.userName())
                .orElseThrow(() -> new IllegalArgumentException("No material found for user: " + user.userName()));


        Inventory originalInventory = inventoryRepository.findFirstByMaterialOrderByWeekDesc(material)
                .orElseThrow(() -> new InventoryNotFound("Inventory not found for user: " + user.userName()));


        PurchaseOrder lastPurchaseOrder = purchaseOrderRepository.findFirstByMaterialOrderByWeekDesc(material)
                .orElseThrow(() -> new PurchasingOrderNotFound("Purchase order not found for user: " + user.userName()));


        int initialInventory = originalInventory.getFinalInventory();
        int finalInventory = initialInventory - dtoUpdatePurchasingOrder.demand() + dtoUpdatePurchasingOrder.orderReceived();
        int orderPlaced = material.getSafetyStock() - finalInventory;

        Inventory newInventory = new Inventory();
        newInventory.setMaterialName(originalInventory.getMaterialName());
        newInventory.setMaterial(material);
        newInventory.setSafetyStock(material.getSafetyStock());
        newInventory.setInitialInventory(initialInventory);
        newInventory.setFinalInventory(finalInventory);
        newInventory.setPendingOrder(orderPlaced);
        newInventory.setDemand(dtoUpdatePurchasingOrder.demand());
        newInventory.setWeek(lastPurchaseOrder.getWeek() + 1);
        newInventory.setInventory_id(Long.valueOf(newInventory.getWeek()));


        PurchaseOrder newPurchaseOrder = new PurchaseOrder();
        newPurchaseOrder.setWeek(lastPurchaseOrder.getWeek() + 1);
        newPurchaseOrder.setDemand(dtoUpdatePurchasingOrder.demand());
        newPurchaseOrder.setOrderPlaced(orderPlaced);
        newPurchaseOrder.setOrderReceived(dtoUpdatePurchasingOrder.orderReceived());
        newPurchaseOrder.setMaterial(material);

        newPurchaseOrder.addInventory(newInventory);


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
    public List<DTOAllPurchaseOrder> getAllPurchasingOrdersBasedMaterialA(@AuthenticationPrincipal Jwt jwt){

        DTOAuth user = new DTOAuth(jwt);

        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();

        return purchaseOrders.stream()
                .filter(purchaseOrder -> purchaseOrder.getInventories() != null &&
                        purchaseOrder.getInventories().stream()
                                .anyMatch(inventory -> "Material A - (Pen)".equals(inventory.getMaterialName()) &&
                                        user.userName().equals(inventory.getMaterial().getUsername())
                                ))
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
}
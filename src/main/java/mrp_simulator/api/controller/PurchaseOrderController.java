package mrp_simulator.api.controller;

import jakarta.validation.Valid;
import mrp_simulator.api.dtos.purchase.DTOAllPurchaseOrder;
import mrp_simulator.api.dtos.purchase.DTODetailUpdatePurchasingOrder;
import mrp_simulator.api.dtos.purchase.DTODetailtFirstWeekPurchase;
import mrp_simulator.api.dtos.purchase.DTOUpdatePurchasingOrder;
import mrp_simulator.api.models.PurchaseOrder;
import mrp_simulator.api.services.purchaseorder.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/purchaseOrder")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;


    // POST
    @PostMapping("/{inventory_id}")
    public ResponseEntity<DTODetailtFirstWeekPurchase> registerFirstWeekPurchaseOrder(@PathVariable Long inventory_id, UriComponentsBuilder uriComponentsBuilder){
        var firstWeekPurchase = purchaseOrderService.registerFirstPurchaseWeek(inventory_id);
        var uri = uriComponentsBuilder.path("/purchaseOrder/{id}").build(firstWeekPurchase.purchase_id());

        return ResponseEntity.created(uri).body(firstWeekPurchase);
    }

    // UPDATE
    @PostMapping("/updatePurchasingOrder/{inventory_id}")
    public ResponseEntity<DTODetailUpdatePurchasingOrder> updateThePurchasingOrder(@RequestBody @Valid DTOUpdatePurchasingOrder dtoUpdatePurchasingOrder, @PathVariable Long inventory_id){
        var updatePurchasingOrder = purchaseOrderService.updatePurchasingOrder(dtoUpdatePurchasingOrder, inventory_id);
        return ResponseEntity.status(HttpStatus.OK).body(updatePurchasingOrder);
    }

    // GET ALL Materials A
    @GetMapping("/allMaterialsA")
    public ResponseEntity<List<DTOAllPurchaseOrder>> getAllPurchasingOrderMaterialA(){
        var listOfPurchasingOrders = purchaseOrderService.getAllPurchasingOrdersBasedMaterialA();
        return ResponseEntity.status(HttpStatus.OK).body(listOfPurchasingOrders);
    }

    // GETT ALL Materials B
    @GetMapping("/allMaterialsB")
    public ResponseEntity<List<DTOAllPurchaseOrder>> getAllPurchasingOrderMaterialB(){
        var listOfPurchasingOrders = purchaseOrderService.getAllPurchasingOrdersBasedMaterialB();
        return ResponseEntity.status(HttpStatus.OK).body(listOfPurchasingOrders);
    }


}
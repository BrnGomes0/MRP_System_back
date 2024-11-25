package mrp_simulator.api.controller;

import jakarta.validation.Valid;
import mrp_simulator.api.dtos.auth.DTOAuth;
import mrp_simulator.api.dtos.purchase.DTOAllPurchaseOrder;
import mrp_simulator.api.dtos.purchase.DTODetailUpdatePurchasingOrder;
import mrp_simulator.api.dtos.purchase.DTODetailtFirstWeekPurchase;
import mrp_simulator.api.dtos.purchase.DTOUpdatePurchasingOrder;
import mrp_simulator.api.services.purchaseorder.PurchaseOrderService;
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
@RequestMapping("/purchaseOrder")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;


    // POST
    @PostMapping
    public ResponseEntity<DTODetailtFirstWeekPurchase> registerFirstWeekPurchaseOrder(UriComponentsBuilder uriComponentsBuilder, @AuthenticationPrincipal Jwt jwt){
        var firstWeekPurchase = purchaseOrderService.registerFirstPurchaseWeek(jwt);
        var uri = uriComponentsBuilder.path("/purchaseOrder/{id}").build(firstWeekPurchase.purchase_id());

        return ResponseEntity.created(uri).body(firstWeekPurchase);
    }

    // UPDATE
    @PostMapping("/updatePurchasingOrder")
    public ResponseEntity<DTODetailUpdatePurchasingOrder> updateThePurchasingOrder(@RequestBody @Valid DTOUpdatePurchasingOrder dtoUpdatePurchasingOrder, @AuthenticationPrincipal Jwt jwt){
        var updatePurchasingOrder = purchaseOrderService.updatePurchasingOrder(dtoUpdatePurchasingOrder, jwt);
        return ResponseEntity.status(HttpStatus.OK).body(updatePurchasingOrder);
    }

    // GET ALL Materials A
    @GetMapping("/allMaterialsA")
    public ResponseEntity<List<DTOAllPurchaseOrder>> getAllPurchasingOrderMaterialA(@AuthenticationPrincipal Jwt jwt){
        var listOfPurchasingOrders = purchaseOrderService.getAllPurchasingOrdersBasedMaterialA(jwt);
        return ResponseEntity.status(HttpStatus.OK).body(listOfPurchasingOrders);
    }
}
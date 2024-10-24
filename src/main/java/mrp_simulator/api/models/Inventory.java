package mrp_simulator.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Inventory")
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventory_id;
    private Integer week;
    private Integer demand;
//    private Integer quantityInInventory;
    private Integer initialInventory;
    private Integer finalInventory;
    private String materialName;
    private Integer safetyStock;
    private Integer pendingOrder;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @OneToOne
    @JoinColumn(name = "purchaseOrder_id")
    private PurchaseOrder relatedPurchaseOrder;

}

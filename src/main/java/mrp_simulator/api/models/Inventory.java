package mrp_simulator.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Inventory")
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long global_id;

    private Long inventory_id;
    private Integer week;
    private Integer demand;
    private Integer initialInventory;
    private Integer finalInventory;
    private String materialName;
    private Integer safetyStock;
    private Integer pendingOrder;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "inventory_purchaseorder",
            joinColumns = @JoinColumn(name = "inventory_id"),
            inverseJoinColumns = @JoinColumn(name = "purchaseOrder_id")
    )
    private List<PurchaseOrder> relatedPurchaseOrders = new ArrayList<>();
}
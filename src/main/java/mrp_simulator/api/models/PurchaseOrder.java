package mrp_simulator.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "PurchaseOrder")
@Table(name = "purchaseorder")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseOrder_id;

    private Integer week;
    private Integer orderPlaced;
    private Integer orderReceived;
    private Integer demand;


    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @ManyToMany(mappedBy = "relatedPurchaseOrders")
    private List<Inventory> inventories = new ArrayList<>();

    public void addInventory(Inventory inventory) {
        inventories.add(inventory);
        inventory.getRelatedPurchaseOrders().add(this);
    }
}

package mrp_simulator.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mrp_simulator.api.dtos.inventory.DTOUpdateInventory;


@Entity(name = "Inventory")
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer week;

    @ManyToOne
    @JoinColumn(name = "material_id", referencedColumnName = "idMaterial")
    private Material material;

    private Integer safetyStock;
    private Integer consumption;
    private Integer inicialInventory;
    private Integer finalInventory;
    private Integer order_received;

    public Inventory(DTOUpdateInventory data){
        this.consumption = data.comsumption();
        this.order_received = data.order_received();
    }
}

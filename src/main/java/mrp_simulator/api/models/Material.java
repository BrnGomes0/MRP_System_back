package mrp_simulator.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mrp_simulator.api.dtos.material.DTORegisterItem;

@Entity(name = "Material")
@Table(name = "material")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMaterial;

    private Integer materialCode;
    private Integer demand;
    private Integer initialInventory;
    private Integer safetyStock;


    public Material(DTORegisterItem registerItem){
        this.materialCode = registerItem.materialCode();
        this.demand = registerItem.demand();
        this.initialInventory = registerItem.inicialInventory();
        this.safetyStock = registerItem.safetyStock();
    }
}

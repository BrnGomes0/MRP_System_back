package mrp_simulator.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Integer inicialInventory;
    private Integer safetyStock;


}

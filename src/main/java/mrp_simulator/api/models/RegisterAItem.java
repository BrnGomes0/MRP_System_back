package mrp_simulator.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "mrp")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterAItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //ID_AutoIncrement
    private Long idMaterial;

    private Integer materialCode;
    private Integer demand;
    private Integer inicialInventory;
    private Integer safetyStock;
}

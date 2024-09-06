package mrp_simulator.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "createInfoRecord")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateAInfoRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInfoRecord;

    private int materialCode;
    private int supplierCode;
    private BigDecimal price;
    private int leadTime;

}

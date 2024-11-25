package mrp_simulator.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "InfoRecord")
@Table(name = "info_record")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InfoRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInfoRecord;

    private String materialCode;

    private Integer supplierCode;
    private String materialText;
    private BigDecimal price;
    private Integer leadTime;

}

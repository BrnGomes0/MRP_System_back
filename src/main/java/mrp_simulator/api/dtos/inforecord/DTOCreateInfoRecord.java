package mrp_simulator.api.dtos.inforecord;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DTOCreateInfoRecord(
        @NotNull BigDecimal price,
        @NotNull int leadTime
        ) {
}

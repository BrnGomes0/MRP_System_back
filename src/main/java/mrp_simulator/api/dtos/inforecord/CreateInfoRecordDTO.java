package mrp_simulator.api.dtos.inforecord;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateInfoRecordDTO(
        @NotNull int materialCode,
        @NotNull int supplierCode,
        @NotNull BigDecimal price,
        @NotNull int leadTime
        ) {
}

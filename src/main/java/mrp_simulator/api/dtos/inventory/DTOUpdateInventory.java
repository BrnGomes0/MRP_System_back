package mrp_simulator.api.dtos.inventory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record DTOUpdateInventory(
        @PositiveOrZero
        @NotBlank
        Integer week,

        @PositiveOrZero
        @NotBlank
        Integer comsumption,

        @PositiveOrZero
        @NotBlank
        Integer order_received
) {
}

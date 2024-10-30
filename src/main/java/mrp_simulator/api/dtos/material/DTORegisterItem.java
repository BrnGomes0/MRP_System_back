package mrp_simulator.api.dtos.material;

import jakarta.validation.constraints.NotNull;

public record DTORegisterItem(
        @NotNull Integer materialCode,
        @NotNull Integer demand,
        @NotNull Integer inicialInventory,
        @NotNull Integer safetyStock
) {
}
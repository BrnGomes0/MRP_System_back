package mrp_simulator.api.dtos.material;

import jakarta.validation.constraints.NotNull;

public record DTORegisterItem(
        @NotNull String materialCode,
        @NotNull Integer demand,
        @NotNull Integer initialInventory,
        @NotNull Integer safetyStock
) {
}
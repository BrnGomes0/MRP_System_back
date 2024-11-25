package mrp_simulator.api.dtos.purchase;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DTOUpdatePurchasingOrder(
        @NotNull
        Integer demand,
        @NotNull
        Integer orderReceived
) {
}

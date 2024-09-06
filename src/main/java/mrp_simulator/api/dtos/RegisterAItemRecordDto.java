package mrp_simulator.api.dtos;

import jakarta.validation.constraints.NotNull;

public record RegisterAItemRecordDto(
        @NotNull int materialCode,
        @NotNull int demand,
        @NotNull int inicialInventory,
        @NotNull int safetyStock
) {
}

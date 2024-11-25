package mrp_simulator.api.dtos.inventory;

public record DTODetailFirstWeek(
        Long id,
        Integer week,
        String materialName,
        Integer safetyStock,
        Integer demand,
        Integer initialInventory,
        Integer finalInventory,
        String username
) {
}

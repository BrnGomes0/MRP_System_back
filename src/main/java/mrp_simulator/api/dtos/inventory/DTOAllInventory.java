package mrp_simulator.api.dtos.inventory;

public record DTOAllInventory(
        Long inventory_id,
        Integer week,
        String materialName,
        Integer demand,
        Integer initialInventory,
        Integer finalInventory,
        Integer safetyStock
) {
}

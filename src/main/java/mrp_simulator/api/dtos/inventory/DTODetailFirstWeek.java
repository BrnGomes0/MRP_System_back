package mrp_simulator.api.dtos.inventory;

public record DTODetailFirstWeek(
        Long id,
        Integer week,
        String materialName,
        Integer demand,
        Integer quantityInInventory
) {
}

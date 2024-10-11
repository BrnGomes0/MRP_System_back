package mrp_simulator.api.dtos.purchase;

public record DTOAllPurchaseOrder(
        Long purchasing_id,
        Integer week,
        Integer orderPlaced,
        Integer orderReceived,
        Integer demand,
        String materialName
) {
}

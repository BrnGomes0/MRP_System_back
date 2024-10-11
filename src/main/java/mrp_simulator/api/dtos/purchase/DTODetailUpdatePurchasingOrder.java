package mrp_simulator.api.dtos.purchase;

public record DTODetailUpdatePurchasingOrder(
        Long purchasing_id,
        Integer week,
        Integer orderReceived,
        Integer orderPlaced,
        String materialName
) {
}

package mrp_simulator.api.dtos.purchase;

public record DTODetailtFirstWeekPurchase(
        Long purchase_id,
        Integer week,
        String material,
        Integer orderPlaced,
        Integer orderReceived
) {
}

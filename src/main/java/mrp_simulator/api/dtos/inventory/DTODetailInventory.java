package mrp_simulator.api.dtos.inventory;

import mrp_simulator.api.models.Inventory;

public record DTODetailInventory(

        Integer week,
        Integer comsumption,
        Integer final_inventory

) {
    public DTODetailInventory(Inventory inventory){
        this(
                inventory.getWeek(),
                inventory.getConsumption(),
                inventory.getFinalInventory()

        );
    }
}

package mrp_simulator.api.dtos.inventory;


import mrp_simulator.api.models.Material;

public record DTORegisterInventory (

    Long inventory_id,
    Integer week,
    Integer safetyStock,
    Integer consumption,
    Integer inicialInventory,
    Integer finalInventory
){
    public DTORegisterInventory(Long inventory_id, Integer week, Integer safetyStock, Integer consumption, Integer inicialInventory, Integer finalInventory){
        this.inventory_id = inventory_id;
        this.week = week;
        this.safetyStock = safetyStock;
        this.consumption = consumption;
        this.inicialInventory = inicialInventory;
        this.finalInventory = finalInventory;
    }
}

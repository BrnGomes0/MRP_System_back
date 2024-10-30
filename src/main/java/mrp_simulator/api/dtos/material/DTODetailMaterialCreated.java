package mrp_simulator.api.dtos.material;

public record DTODetailMaterialCreated(
        Long material_id,
        Integer material_code,
        Integer demand,
        Integer inicialInventory,
        Integer safetyStock
){
}

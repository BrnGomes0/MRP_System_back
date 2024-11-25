package mrp_simulator.api.dtos.material;

public record DTODetailMaterialCreated(
        Long material_id,
        String material_code,
        Integer demand,
        Integer initialInventory,
        Integer safetyStock,
        String userName
){
}

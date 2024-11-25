package mrp_simulator.api.repositories;

import mrp_simulator.api.models.Inventory;
import mrp_simulator.api.models.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long>{
    boolean existsByWeekAndMaterial_Username(int week, String username);
    List<Inventory> findByMaterialUsername(String username);
    Optional<Inventory> findFirstByMaterial_Username(String username);
    Optional<Inventory> findFirstByMaterialOrderByWeekDesc(Material material);
}
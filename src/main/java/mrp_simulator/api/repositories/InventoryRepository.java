package mrp_simulator.api.repositories;

import mrp_simulator.api.models.Inventory;
import mrp_simulator.api.models.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long>{
    boolean existsByMaterialAndWeek(Material material, Integer week);
}

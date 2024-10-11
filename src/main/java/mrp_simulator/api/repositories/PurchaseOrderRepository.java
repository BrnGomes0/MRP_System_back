package mrp_simulator.api.repositories;

import mrp_simulator.api.models.Inventory;
import mrp_simulator.api.models.Material;
import mrp_simulator.api.models.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    boolean existsByInventoryAndWeek(Inventory Inventory, Integer week);
    Optional<PurchaseOrder> findFirstByInventoryOrderByWeekDesc(Inventory inventory);
}

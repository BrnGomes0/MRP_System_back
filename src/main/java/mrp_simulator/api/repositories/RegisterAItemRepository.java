package mrp_simulator.api.repositories;

import mrp_simulator.api.models.RegisterAItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisterAItemRepository extends JpaRepository<RegisterAItem, Long> {
    Optional<RegisterAItem> findById(Long idMaterial);
    Optional<RegisterAItem> findByMaterialCode(int materialCode);
    Optional<RegisterAItem> findFirstByOrderByIdMaterialDesc();
}

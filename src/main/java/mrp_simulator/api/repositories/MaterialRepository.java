package mrp_simulator.api.repositories;

import mrp_simulator.api.models.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    Optional<Material> findById(Long idMaterial);
    Optional<Material> findByMaterialCode(int materialCode);
    Optional<Material> findFirstByOrderByIdMaterialDesc();;
}

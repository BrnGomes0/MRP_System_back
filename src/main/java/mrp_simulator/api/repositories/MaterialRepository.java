package mrp_simulator.api.repositories;

import mrp_simulator.api.models.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    Optional<Material> findById(Long idMaterial);
    Optional<Material> findByMaterialCode(String materialCode);
    Optional<Material> findFirstByOrderByIdMaterialDesc();
    Optional<Material> findByUsername(String username);
    boolean existsByUsername(String username);
}

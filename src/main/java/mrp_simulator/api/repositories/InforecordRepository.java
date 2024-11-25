package mrp_simulator.api.repositories;

import mrp_simulator.api.models.InfoRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InforecordRepository extends JpaRepository<InfoRecord, Long> {
    Optional<InfoRecord> findByMaterialCode(String materialCode);
    Optional<InfoRecord> findById(Long idInfoRecord);

}

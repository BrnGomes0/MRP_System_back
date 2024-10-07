package mrp_simulator.api.repositories;

import mrp_simulator.api.models.CreateAInfoRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreateAInfoRecordRepository extends JpaRepository<CreateAInfoRecord, Long> {
    Optional<CreateAInfoRecord> findByMaterialCode(int materialCode);
    Optional<CreateAInfoRecord> findById(Long idInfoRecord);
}

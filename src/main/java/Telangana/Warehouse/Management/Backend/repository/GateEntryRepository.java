
package Telangana.Warehouse.Management.Backend.repository;

import Telangana.Warehouse.Management.Backend.entity.GateEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GateEntryRepository extends JpaRepository<GateEntry, Long> {

    List<GateEntry> findByStatus(String status);

    Optional<GateEntry> findByIdAndStatus(Long id, String status);

    boolean existsByTruckNumberIgnoreCaseAndStatus(
            String truckNumber,
            String status
    );
}
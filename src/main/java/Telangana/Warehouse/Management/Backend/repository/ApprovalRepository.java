package Telangana.Warehouse.Management.Backend.repository;

import Telangana.Warehouse.Management.Backend.entity.Approval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {

    List<Approval> findByStatusIgnoreCase(String status);

    Optional<Approval> findByGateEntryId(Long gateEntryId);

    boolean existsByGateEntryId(Long gateEntryId);
}


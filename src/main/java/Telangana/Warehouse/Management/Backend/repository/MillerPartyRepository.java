package Telangana.Warehouse.Management.Backend.repository;

import Telangana.Warehouse.Management.Backend.entity.MillerParty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MillerPartyRepository
        extends JpaRepository<MillerParty, Long> {

    Optional<MillerParty> findByPartyCode(String partyCode);

    boolean existsByPartyCode(String partyCode);
}
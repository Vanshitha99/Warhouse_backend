package Telangana.Warehouse.Management.Backend.repository;

import Telangana.Warehouse.Management.Backend.entity.TruckManagement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TruckManagementRepository
        extends JpaRepository<TruckManagement, Long> {

    Optional<TruckManagement> findByTruckNumberIgnoreCase(String truckNumber);
}
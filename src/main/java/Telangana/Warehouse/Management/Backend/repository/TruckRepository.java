package Telangana.Warehouse.Management.Backend.repository;

import Telangana.Warehouse.Management.Backend.entity.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TruckRepository extends JpaRepository<Truck, Long> {

    Optional<Truck> findByTruckNumber(String truckNumber);

    boolean existsByTruckNumber(String truckNumber);
}
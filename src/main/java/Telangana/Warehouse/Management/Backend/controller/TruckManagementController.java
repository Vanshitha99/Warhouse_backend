package Telangana.Warehouse.Management.Backend.controller;

import Telangana.Warehouse.Management.Backend.entity.TruckManagement;
import Telangana.Warehouse.Management.Backend.repository.TruckManagementRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trucks")
@CrossOrigin(origins = "http://localhost:5173")
public class TruckManagementController {

    private final TruckManagementRepository truckRepository;

    public TruckManagementController(
            TruckManagementRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    @GetMapping
    public List<TruckManagement> getAllTrucks() {
        return truckRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> addTruck(
            @RequestBody TruckManagement truck) {

        if (truck.getTruckNumber() == null
                || truck.getTruckNumber().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Truck number is required"));
        }

        if (truck.getLoadedWeight() == null
                || truck.getEmptyWeight() == null
                || truck.getNumberOfBags() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error",
                            "Loaded weight, empty weight and number of bags are required"));
        }

        if (truck.getLoadedWeight() <= truck.getEmptyWeight()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error",
                            "Loaded weight must be greater than empty weight"));
        }

        if (truck.getNumberOfBags() <= 0) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error",
                            "Number of bags must be greater than zero"));
        }

        if (truckRepository.findByTruckNumberIgnoreCase(
                truck.getTruckNumber().trim()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Truck number already exists"));
        }

        truck.setTruckNumber(truck.getTruckNumber().trim());
        truck.setGrossWeight(
                truck.getLoadedWeight() - truck.getEmptyWeight());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(truckRepository.save(truck));
    }
}


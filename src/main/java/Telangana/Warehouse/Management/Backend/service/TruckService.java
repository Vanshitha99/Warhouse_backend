package Telangana.Warehouse.Management.Backend.service;

import Telangana.Warehouse.Management.Backend.entity.Truck;
import Telangana.Warehouse.Management.Backend.repository.TruckRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TruckService {

    private final TruckRepository truckRepository;

    public TruckService(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    // Save a new truck
    public Truck saveTruck(Truck truck) {

        // Check truck number
        if (truck.getTruckNumber() == null ||
                truck.getTruckNumber().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Truck number is required"
            );
        }

        // Check weights
        if (truck.getLoadedWeight() == null ||
                truck.getEmptyWeight() == null) {

            throw new IllegalArgumentException(
                    "Loaded weight and empty weight are required"
            );
        }

        // Check negative weights
        if (truck.getLoadedWeight() < 0 ||
                truck.getEmptyWeight() < 0) {

            throw new IllegalArgumentException(
                    "Weights cannot be negative"
            );
        }

        // Loaded weight cannot be less than empty weight
        if (truck.getLoadedWeight() <
                truck.getEmptyWeight()) {

            throw new IllegalArgumentException(
                    "Loaded weight cannot be less than empty weight"
            );
        }

        // Check number of bags
        if (truck.getNumberOfBags() == null ||
                truck.getNumberOfBags() < 0) {

            throw new IllegalArgumentException(
                    "Number of bags cannot be negative"
            );
        }

        // Automatically calculate commodity weight
        double grossCommodityWeight =
                truck.getLoadedWeight()
                        - truck.getEmptyWeight();

        truck.setGrossCommodityWeight(
                grossCommodityWeight
        );

        // Save to MySQL
        return truckRepository.save(truck);
    }

    // Find truck by truck number
    public Optional<Truck> getTruckByNumber(
            String truckNumber) {

        return truckRepository.findByTruckNumber(
                truckNumber
        );
    }

    // Get all trucks
    public List<Truck> getAllTrucks() {

        return truckRepository.findAll();
    }

    // Check whether truck exists
    public boolean truckExists(String truckNumber) {

        return truckRepository.existsByTruckNumber(
                truckNumber
        );
    }

    // Delete truck
    public void deleteTruck(Long id) {

        truckRepository.deleteById(id);
    }
}
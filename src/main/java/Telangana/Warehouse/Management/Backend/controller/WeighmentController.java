package Telangana.Warehouse.Management.Backend.controller;

import Telangana.Warehouse.Management.Backend.entity.Weighment;
import Telangana.Warehouse.Management.Backend.repository.WeighmentRepository;
import Telangana.Warehouse.Management.Backend.repository.TruckManagementRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weighments")
@CrossOrigin(origins = "*")
public class WeighmentController {

    private final WeighmentRepository weighmentRepository;
    private final TruckManagementRepository truckRepository;

    public WeighmentController(
            WeighmentRepository weighmentRepository,
            TruckManagementRepository truckRepository) {

        this.weighmentRepository = weighmentRepository;
        this.truckRepository = truckRepository;
    }

    @PostMapping
    public Weighment addWeighment(
            @jakarta.validation.Valid
            @RequestBody Weighment weighment) {

        checkTruckExists(weighment.getTruckNumber());

        return weighmentRepository.save(weighment);
    }

    @GetMapping
    public List<Weighment> getAllWeighments() {
        return weighmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Weighment getWeighmentById(@PathVariable Long id) {

        return weighmentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Weighment not found"));
    }

    @PutMapping("/{id}")
    public Weighment updateWeighment(
            @PathVariable Long id,
            @jakarta.validation.Valid
            @RequestBody Weighment weighmentDetails) {

        Weighment weighment =
                weighmentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Weighment not found"));

        checkTruckExists(weighmentDetails.getTruckNumber());

        weighment.setTruckNumber(
                weighmentDetails.getTruckNumber());

        weighment.setPartyName(
                weighmentDetails.getPartyName());

        weighment.setPartyCode(
                weighmentDetails.getPartyCode());

        weighment.setAcknowledgementNumber(
                weighmentDetails.getAcknowledgementNumber());

        weighment.setWeighBillNumber(
                weighmentDetails.getWeighBillNumber());

        weighment.setLoadedWeight(
                weighmentDetails.getLoadedWeight());

        weighment.setStackNumber(
                weighmentDetails.getStackNumber());

        weighment.setGodownNumber(
                weighmentDetails.getGodownNumber());

        return weighmentRepository.save(weighment);
    }

    @DeleteMapping("/{id}")
    public String deleteWeighment(@PathVariable Long id) {

        Weighment weighment =
                weighmentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Weighment not found"));

        weighmentRepository.delete(weighment);

        return "Weighment deleted successfully";
    }

    private void checkTruckExists(String truckNumber) {

        boolean exists =
                truckRepository.findAll()
                        .stream()
                        .anyMatch(truck ->
                                truck.getTruckNumber()
                                        .equalsIgnoreCase(truckNumber));

        if (!exists) {
            throw new RuntimeException(
                    "Truck number not found in Truck Management");
        }
    }
}
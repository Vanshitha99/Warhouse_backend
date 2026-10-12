
        package Telangana.Warehouse.Management.Backend.service;

import Telangana.Warehouse.Management.Backend.entity.Approval;
import Telangana.Warehouse.Management.Backend.entity.GateEntry;
import Telangana.Warehouse.Management.Backend.entity.MillerParty;
import Telangana.Warehouse.Management.Backend.entity.TruckManagement;
import Telangana.Warehouse.Management.Backend.repository.ApprovalRepository;
import Telangana.Warehouse.Management.Backend.repository.GateEntryRepository;
import Telangana.Warehouse.Management.Backend.repository.MillerPartyRepository;
import Telangana.Warehouse.Management.Backend.repository.TruckManagementRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GateEntryService {

    private final GateEntryRepository gateEntryRepository;
    private final MillerPartyRepository millerPartyRepository;
    private final TruckManagementRepository truckManagementRepository;
    private final ApprovalRepository approvalRepository;

    public GateEntryService(
            GateEntryRepository gateEntryRepository,
            MillerPartyRepository millerPartyRepository,
            TruckManagementRepository truckManagementRepository,
            ApprovalRepository approvalRepository) {

        this.gateEntryRepository = gateEntryRepository;
        this.millerPartyRepository = millerPartyRepository;
        this.truckManagementRepository = truckManagementRepository;
        this.approvalRepository = approvalRepository;
    }

    // Create a gate entry and its approval request together.
    @Transactional
    public GateEntry createGateEntry(
            String partyCode,
            String truckNumber,
            String commodity,
            Integer numberOfBags) {

        if (partyCode == null || partyCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Party code is required");
        }

        if (truckNumber == null || truckNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Truck number is required");
        }

        if (commodity == null || commodity.trim().isEmpty()) {
            throw new IllegalArgumentException("Commodity is required");
        }

        if (numberOfBags == null || numberOfBags <= 0) {
            throw new IllegalArgumentException(
                    "Number of bags must be greater than zero");
        }

        String normalizedTruckNumber = truckNumber.trim();

        if (gateEntryRepository.existsByTruckNumberIgnoreCaseAndStatus(
                normalizedTruckNumber, "INSIDE")) {
            throw new IllegalArgumentException(
                    "This truck already has an active gate entry");
        }

        MillerParty party = millerPartyRepository
                .findByPartyCode(partyCode.trim())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Party not found: " + partyCode));

        TruckManagement truck = truckManagementRepository
                .findByTruckNumberIgnoreCase(normalizedTruckNumber)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Truck not found: " + normalizedTruckNumber));

        GateEntry gateEntry = new GateEntry();
        gateEntry.setParty(party);
        gateEntry.setPartyName(party.getPartyName());
        gateEntry.setTruck(truck);
        gateEntry.setTruckNumber(truck.getTruckNumber());
        gateEntry.setCommodity(commodity.trim());
        gateEntry.setNumberOfBags(numberOfBags);
        gateEntry.setInTime(LocalDateTime.now());
        gateEntry.setStatus("INSIDE");

        // Save the gate entry first to generate its database ID.
        GateEntry savedEntry = gateEntryRepository.save(gateEntry);

        // Automatically create a matching pending approval.
        Approval approval = new Approval();
        approval.setGateEntryId(savedEntry.getId());
        approval.setTransactionType("Gate Entry");
        approval.setStatus("PENDING");
        approvalRepository.save(approval);

        // Both writes occur in the same transaction.
        return savedEntry;
    }

    @Transactional(readOnly = true)
    public List<GateEntry> getAllGateEntries() {
        return gateEntryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<GateEntry> getInsideEntries() {
        return gateEntryRepository.findByStatus("INSIDE");
    }

    @Transactional
    public GateEntry markTruckOut(Long id) {
        GateEntry gateEntry = gateEntryRepository
                .findByIdAndStatus(id, "INSIDE")
                .orElseThrow(() -> new IllegalArgumentException(
                        "Active gate entry not found: " + id));

        gateEntry.setOutTime(LocalDateTime.now());
        gateEntry.setStatus("EXITED");

        return gateEntryRepository.save(gateEntry);
    }
}


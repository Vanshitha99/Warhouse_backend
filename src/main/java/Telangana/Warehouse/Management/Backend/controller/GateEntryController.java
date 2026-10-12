package Telangana.Warehouse.Management.Backend.controller;

import Telangana.Warehouse.Management.Backend.entity.GateEntry;
import Telangana.Warehouse.Management.Backend.service.GateEntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gate-entries")
@CrossOrigin(origins = "http://localhost:5173")
public class GateEntryController {

    private final GateEntryService gateEntryService;

    public GateEntryController(
            GateEntryService gateEntryService) {

        this.gateEntryService = gateEntryService;
    }

    // Create Gate Entry
    @PostMapping
    public ResponseEntity<GateEntry> createGateEntry(
            @RequestParam String partyCode,
            @RequestParam String truckNumber,
            @RequestParam String commodity,
            @RequestParam Integer numberOfBags) {

        GateEntry gateEntry =
                gateEntryService.createGateEntry(
                        partyCode,
                        truckNumber,
                        commodity,
                        numberOfBags
                );

        return ResponseEntity.ok(gateEntry);
    }

    // Get all Gate Entries
    @GetMapping
    public ResponseEntity<List<GateEntry>> getAllGateEntries() {

        return ResponseEntity.ok(
                gateEntryService.getAllGateEntries()
        );
    }

    // Get trucks currently inside
    @GetMapping("/inside")
    public ResponseEntity<List<GateEntry>> getInsideEntries() {

        return ResponseEntity.ok(
                gateEntryService.getInsideEntries()
        );
    }

    // Mark truck out
    @PutMapping("/{id}/out")
    public ResponseEntity<GateEntry> markTruckOut(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                gateEntryService.markTruckOut(id)
        );
    }
}
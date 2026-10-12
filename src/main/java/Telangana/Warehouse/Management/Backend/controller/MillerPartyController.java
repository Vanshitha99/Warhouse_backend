
package Telangana.Warehouse.Management.Backend.controller;

import Telangana.Warehouse.Management.Backend.entity.MillerParty;
import Telangana.Warehouse.Management.Backend.repository.MillerPartyRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/miller-parties")
@CrossOrigin(origins = "http://localhost:5173")
public class MillerPartyController {

    private final MillerPartyRepository millerPartyRepository;

    public MillerPartyController(
            MillerPartyRepository millerPartyRepository) {
        this.millerPartyRepository = millerPartyRepository;
    }

    @GetMapping
    public ResponseEntity<List<MillerParty>> getAllParties() {
        return ResponseEntity.ok(millerPartyRepository.findAll());
    }

    @GetMapping("/{partyCode}")
    public ResponseEntity<MillerParty> getPartyByCode(
            @PathVariable String partyCode) {

        return millerPartyRepository.findByPartyCode(partyCode)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
package Telangana.Warehouse.Management.Backend.service;

import Telangana.Warehouse.Management.Backend.entity.MillerParty;
import Telangana.Warehouse.Management.Backend.repository.MillerPartyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MillerPartyService {

    private final MillerPartyRepository millerPartyRepository;

    public MillerPartyService(
            MillerPartyRepository millerPartyRepository) {

        this.millerPartyRepository = millerPartyRepository;
    }

    public MillerParty saveParty(MillerParty party) {
        return millerPartyRepository.save(party);
    }

    public Optional<MillerParty> getPartyByCode(String partyCode) {
        return millerPartyRepository.findByPartyCode(partyCode);
    }

    public List<MillerParty> getAllParties() {
        return millerPartyRepository.findAll();
    }

    public boolean partyCodeExists(String partyCode) {
        return millerPartyRepository.existsByPartyCode(partyCode);
    }
}
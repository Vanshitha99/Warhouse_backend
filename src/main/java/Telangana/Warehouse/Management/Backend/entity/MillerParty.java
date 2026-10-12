package Telangana.Warehouse.Management.Backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "miller_parties")
public class MillerParty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String partyCode;

    @Column(nullable = false)
    private String partyName;

    @Column(nullable = false)
    private boolean active = true;

    public MillerParty() {
    }

    public MillerParty(String partyCode, String partyName) {
        this.partyCode = partyCode;
        this.partyName = partyName;
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public String getPartyCode() {
        return partyCode;
    }

    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
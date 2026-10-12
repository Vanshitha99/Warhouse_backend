
package Telangana.Warehouse.Management.Backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "gate_entries")
public class GateEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "party_id", nullable = false)
    private MillerParty party;

    @Column(name = "party_name", nullable = false)
    private String partyName;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "truck_id", nullable = false)
    private TruckManagement truck;

    @Column(name = "truck_number", nullable = false)
    private String truckNumber;

    @Column(nullable = false)
    private String commodity;

    @Column(name = "number_of_bags", nullable = false)
    private Integer numberOfBags;

    @Column(name = "in_time", nullable = false)
    private LocalDateTime inTime;

    @Column(name = "out_time")
    private LocalDateTime outTime;

    @Column(nullable = false)
    private String status;

    // Default constructor
    public GateEntry() {
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public MillerParty getParty() {
        return party;
    }

    public void setParty(MillerParty party) {
        this.party = party;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public TruckManagement getTruck() {
        return truck;
    }

    public void setTruck(TruckManagement truck) {
        this.truck = truck;
    }

    public String getTruckNumber() {
        return truckNumber;
    }

    public void setTruckNumber(String truckNumber) {
        this.truckNumber = truckNumber;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public Integer getNumberOfBags() {
        return numberOfBags;
    }

    public void setNumberOfBags(Integer numberOfBags) {
        this.numberOfBags = numberOfBags;
    }

    public LocalDateTime getInTime() {
        return inTime;
    }

    public void setInTime(LocalDateTime inTime) {
        this.inTime = inTime;
    }

    public LocalDateTime getOutTime() {
        return outTime;
    }

    public void setOutTime(LocalDateTime outTime) {
        this.outTime = outTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
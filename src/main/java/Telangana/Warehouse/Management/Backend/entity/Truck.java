
        package Telangana.Warehouse.Management.Backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "trucks")
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String truckNumber;

    @Column(nullable = false)
    private Double loadedWeight;

    @Column(nullable = false)
    private Double emptyWeight;

    @Column(nullable = false)
    private Double grossCommodityWeight;

    @Column(nullable = false)
    private Integer numberOfBags;

    // Automatically saved when the truck is registered
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Default constructor required by JPA
    public Truck() {
    }

    // Constructor
    public Truck(
            String truckNumber,
            Double loadedWeight,
            Double emptyWeight,
            Integer numberOfBags) {

        this.truckNumber = truckNumber;
        this.loadedWeight = loadedWeight;
        this.emptyWeight = emptyWeight;
        this.numberOfBags = numberOfBags;

        calculateGrossCommodityWeight();
    }

    // Automatically set registration time before saving
    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public String getTruckNumber() {
        return truckNumber;
    }

    public void setTruckNumber(String truckNumber) {
        this.truckNumber = truckNumber;
    }

    public Double getLoadedWeight() {
        return loadedWeight;
    }

    public void setLoadedWeight(Double loadedWeight) {
        this.loadedWeight = loadedWeight;
        calculateGrossCommodityWeight();
    }

    public Double getEmptyWeight() {
        return emptyWeight;
    }

    public void setEmptyWeight(Double emptyWeight) {
        this.emptyWeight = emptyWeight;
        calculateGrossCommodityWeight();
    }

    public Double getGrossCommodityWeight() {
        return grossCommodityWeight;
    }

    public void setGrossCommodityWeight(
            Double grossCommodityWeight) {
        this.grossCommodityWeight = grossCommodityWeight;
    }

    public Integer getNumberOfBags() {
        return numberOfBags;
    }

    public void setNumberOfBags(Integer numberOfBags) {
        this.numberOfBags = numberOfBags;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    private void calculateGrossCommodityWeight() {
        if (loadedWeight != null && emptyWeight != null) {
            this.grossCommodityWeight =
                    loadedWeight - emptyWeight;
        }
    }
}


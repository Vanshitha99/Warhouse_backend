package Telangana.Warehouse.Management.Backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "truck_management")
@Data
@NoArgsConstructor
public class TruckManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Truck number is required")
    @Column(nullable = false)
    private String truckNumber;

    @NotNull(message = "Loaded weight is required")
    @PositiveOrZero(message = "Loaded weight cannot be negative")
    @Column(nullable = false)
    private Double loadedWeight;

    @NotNull(message = "Empty weight is required")
    @PositiveOrZero(message = "Empty weight cannot be negative")
    @Column(nullable = false)
    private Double emptyWeight;

    @Column(nullable = false)
    private Double grossWeight;

    @NotNull(message = "Number of bags is required")
    @PositiveOrZero(message = "Number of bags cannot be negative")
    @Column(nullable = false)
    private Integer numberOfBags;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        calculateGrossWeight();
    }

    @PreUpdate
    protected void onUpdate() {
        calculateGrossWeight();
    }

    private void calculateGrossWeight() {
        if (loadedWeight != null && emptyWeight != null) {
            grossWeight = loadedWeight - emptyWeight;
        }
    }
}


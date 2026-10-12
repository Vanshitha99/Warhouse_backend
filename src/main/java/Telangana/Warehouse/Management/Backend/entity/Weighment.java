
package Telangana.Warehouse.Management.Backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "weighments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Weighment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Truck number is required")
    @Column(nullable = false)
    private String truckNumber;

    @NotBlank(message = "Party name is required")
    @Column(nullable = false)
    private String partyName;

    @NotBlank(message = "Party code is required")
    @Column(nullable = false)
    private String partyCode;

    @NotBlank(message = "Acknowledgement number is required")
    @Column(nullable = false, unique = true)
    private String acknowledgementNumber;

    @NotBlank(message = "Weigh bill number is required")
    @Column(nullable = false, unique = true)
    private String weighBillNumber;

    @NotNull(message = "Loaded weight is required")
    @PositiveOrZero(message = "Loaded weight cannot be negative")
    @Column(nullable = false)
    private Double loadedWeight;

    @NotBlank(message = "Stack number is required")
    @Column(nullable = false)
    private String stackNumber;

    @NotBlank(message = "Godown number is required")
    @Column(nullable = false)
    private String godownNumber;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}


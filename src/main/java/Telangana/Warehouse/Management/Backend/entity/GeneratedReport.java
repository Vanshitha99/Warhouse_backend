package Telangana.Warehouse.Management.Backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "generated_reports")
@Getter
@Setter
@NoArgsConstructor
public class GeneratedReport {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String reportId;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String period;

    @Column(nullable = false)
    private LocalDateTime generatedAt;

    @Column(nullable = false)
    private String status;

    private Integer gateEntryCount;

    private Integer uniqueTruckCount;

    private Integer weighmentCount;

    private Integer exitedEntryCount;

    @PrePersist
    protected void onCreate() {
        if (generatedAt == null) {
            generatedAt = LocalDateTime.now();
        }

        if (status == null || status.isBlank()) {
            status = "Generated";
        }
    }

}

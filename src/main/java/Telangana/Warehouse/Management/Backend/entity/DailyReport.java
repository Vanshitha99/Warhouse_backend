package Telangana.Warehouse.Management.Backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "daily_reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate reportDate;

    @Column(nullable = false)
    private String commodity;

    @Column(nullable = false)
    private String variety;

    @Column(nullable = false)
    private Integer openingBags;

    @Column(nullable = false)
    private Double openingWeight;

    @Column(nullable = false)
    private Integer receiptBags;

    @Column(nullable = false)
    private Double receiptWeight;

    @Column(nullable = false)
    private Integer issueBags;

    @Column(nullable = false)
    private Double issueWeight;

    @Column(nullable = false)
    private Integer closingBags;

    @Column(nullable = false)
    private Double closingWeight;
}
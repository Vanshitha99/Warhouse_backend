package Telangana.Warehouse.Management.Backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "monthly_reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer month;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private String commodity;

    @Column(nullable = false)
    private String variety;

    @Column(nullable = false)
    private Integer openingBags;

    @Column(nullable = false)
    private Double openingWeight;

    @Column(nullable = false)
    private Integer totalReceiptBags;

    @Column(nullable = false)
    private Double totalReceiptWeight;

    @Column(nullable = false)
    private Integer totalIssueBags;

    @Column(nullable = false)
    private Double totalIssueWeight;

    @Column(nullable = false)
    private Integer closingBags;

    @Column(nullable = false)
    private Double closingWeight;
}
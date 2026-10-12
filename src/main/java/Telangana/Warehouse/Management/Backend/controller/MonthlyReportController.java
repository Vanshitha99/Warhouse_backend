package Telangana.Warehouse.Management.Backend.controller;

import Telangana.Warehouse.Management.Backend.entity.MonthlyReport;
import Telangana.Warehouse.Management.Backend.repository.MonthlyReportRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monthly-reports")
@CrossOrigin(origins = "*")
public class MonthlyReportController {

    private final MonthlyReportRepository monthlyReportRepository;

    public MonthlyReportController(
            MonthlyReportRepository monthlyReportRepository) {
        this.monthlyReportRepository = monthlyReportRepository;
    }

    // Add monthly report
    @PostMapping
    public MonthlyReport addMonthlyReport(
            @RequestBody MonthlyReport report) {

        calculateClosingValues(report);

        return monthlyReportRepository.save(report);
    }

    // Get all monthly reports
    @GetMapping
    public List<MonthlyReport> getAllMonthlyReports() {
        return monthlyReportRepository.findAll();
    }

    // Get monthly report by ID
    @GetMapping("/{id}")
    public MonthlyReport getMonthlyReportById(
            @PathVariable Long id) {

        return monthlyReportRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Monthly report not found"));
    }

    // Update monthly report
    @PutMapping("/{id}")
    public MonthlyReport updateMonthlyReport(
            @PathVariable Long id,
            @RequestBody MonthlyReport details) {

        MonthlyReport report = monthlyReportRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Monthly report not found"));

        report.setMonth(details.getMonth());
        report.setYear(details.getYear());
        report.setCommodity(details.getCommodity());
        report.setVariety(details.getVariety());

        report.setOpeningBags(details.getOpeningBags());
        report.setOpeningWeight(details.getOpeningWeight());

        report.setTotalReceiptBags(details.getTotalReceiptBags());
        report.setTotalReceiptWeight(details.getTotalReceiptWeight());

        report.setTotalIssueBags(details.getTotalIssueBags());
        report.setTotalIssueWeight(details.getTotalIssueWeight());

        calculateClosingValues(report);

        return monthlyReportRepository.save(report);
    }

    // Delete monthly report
    @DeleteMapping("/{id}")
    public String deleteMonthlyReport(
            @PathVariable Long id) {

        MonthlyReport report = monthlyReportRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Monthly report not found"));

        monthlyReportRepository.delete(report);

        return "Monthly report deleted successfully";
    }

    // Calculate closing values
    private void calculateClosingValues(MonthlyReport report) {

        int closingBags =
                report.getOpeningBags()
                        + report.getTotalReceiptBags()
                        - report.getTotalIssueBags();

        double closingWeight =
                report.getOpeningWeight()
                        + report.getTotalReceiptWeight()
                        - report.getTotalIssueWeight();

        report.setClosingBags(closingBags);
        report.setClosingWeight(closingWeight);
    }
}
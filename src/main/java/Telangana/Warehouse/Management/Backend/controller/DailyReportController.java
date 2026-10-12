package Telangana.Warehouse.Management.Backend.controller;

import Telangana.Warehouse.Management.Backend.entity.DailyReport;
import Telangana.Warehouse.Management.Backend.repository.DailyReportRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/daily-reports")
@CrossOrigin(origins = "*")
public class DailyReportController {

    private final DailyReportRepository dailyReportRepository;

    public DailyReportController(
            DailyReportRepository dailyReportRepository) {
        this.dailyReportRepository = dailyReportRepository;
    }

    // Add daily report
    @PostMapping
    public DailyReport addDailyReport(
            @RequestBody DailyReport report) {

        calculateClosingValues(report);

        return dailyReportRepository.save(report);
    }

    // Get all daily reports
    @GetMapping
    public List<DailyReport> getAllDailyReports() {
        return dailyReportRepository.findAll();
    }

    // Get daily report by ID
    @GetMapping("/{id}")
    public DailyReport getDailyReportById(
            @PathVariable Long id) {

        return dailyReportRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Daily report not found"));
    }

    // Update daily report
    @PutMapping("/{id}")
    public DailyReport updateDailyReport(
            @PathVariable Long id,
            @RequestBody DailyReport details) {

        DailyReport report = dailyReportRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Daily report not found"));

        report.setReportDate(details.getReportDate());
        report.setCommodity(details.getCommodity());
        report.setVariety(details.getVariety());

        report.setOpeningBags(details.getOpeningBags());
        report.setOpeningWeight(details.getOpeningWeight());

        report.setReceiptBags(details.getReceiptBags());
        report.setReceiptWeight(details.getReceiptWeight());

        report.setIssueBags(details.getIssueBags());
        report.setIssueWeight(details.getIssueWeight());

        calculateClosingValues(report);

        return dailyReportRepository.save(report);
    }

    // Delete daily report
    @DeleteMapping("/{id}")
    public String deleteDailyReport(
            @PathVariable Long id) {

        DailyReport report = dailyReportRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Daily report not found"));

        dailyReportRepository.delete(report);

        return "Daily report deleted successfully";
    }

    // Calculate closing balance
    private void calculateClosingValues(DailyReport report) {

        int closingBags =
                report.getOpeningBags()
                        + report.getReceiptBags()
                        - report.getIssueBags();

        double closingWeight =
                report.getOpeningWeight()
                        + report.getReceiptWeight()
                        - report.getIssueWeight();

        report.setClosingBags(closingBags);
        report.setClosingWeight(closingWeight);
    }
}
package Telangana.Warehouse.Management.Backend.controller;

import Telangana.Warehouse.Management.Backend.entity.GeneratedReport;
import Telangana.Warehouse.Management.Backend.repository.GeneratedReportRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/generated-reports")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174"
})
public class GeneratedReportController {


    private final GeneratedReportRepository reportRepository;

    public GeneratedReportController(
            GeneratedReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @GetMapping
    public List<GeneratedReport> getAllReports() {
        return reportRepository.findAll();
    }

    @GetMapping("/{id}")
    public GeneratedReport getReportById(@PathVariable Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Report not found: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GeneratedReport createReport(
            @RequestBody GeneratedReport report) {

        if (report.getType() == null ||
                !(report.getType().equalsIgnoreCase("Daily") ||
                        report.getType().equalsIgnoreCase("Monthly"))) {
            throw new IllegalArgumentException(
                    "Report type must be Daily or Monthly");
        }

        if (report.getTitle() == null ||
                report.getTitle().isBlank()) {
            throw new IllegalArgumentException(
                    "Report title is required");
        }

        if (report.getPeriod() == null ||
                report.getPeriod().isBlank()) {
            throw new IllegalArgumentException(
                    "Report period is required");
        }

        if (report.getReportId() == null ||
                report.getReportId().isBlank()) {
            report.setReportId(
                    "RPT-" + java.util.UUID.randomUUID()
                            .toString().substring(0, 8).toUpperCase());
        }

        report.setId(null);
        report.setGeneratedAt(null);
        report.setStatus("Generated");

        return reportRepository.save(report);
    }


}

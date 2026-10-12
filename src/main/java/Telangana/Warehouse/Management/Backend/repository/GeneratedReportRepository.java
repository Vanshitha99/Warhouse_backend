package Telangana.Warehouse.Management.Backend.repository;

import Telangana.Warehouse.Management.Backend.entity.GeneratedReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GeneratedReportRepository
        extends JpaRepository<GeneratedReport, Long> {


    Optional<GeneratedReport> findByReportId(String reportId);


}

package Telangana.Warehouse.Management.Backend.repository;

import Telangana.Warehouse.Management.Backend.entity.DailyReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyReportRepository
        extends JpaRepository<DailyReport, Long> {
}
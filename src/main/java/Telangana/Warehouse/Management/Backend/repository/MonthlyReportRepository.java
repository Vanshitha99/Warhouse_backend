package Telangana.Warehouse.Management.Backend.repository;

import Telangana.Warehouse.Management.Backend.entity.MonthlyReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyReportRepository
        extends JpaRepository<MonthlyReport, Long> {
}
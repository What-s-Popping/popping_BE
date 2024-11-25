package com.popping.data.report.repository;

import com.popping.data.report.entity.RePopReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RePopReportRepository extends JpaRepository<RePopReport, Long> {
    boolean existsByReporter_PkAndTarget_Pk(Long reporterPk, Long targetPk);
}

package com.popping.data.report.repository;

import com.popping.data.report.entity.PopReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopReportRepository extends JpaRepository<PopReport, Long> {
    boolean existsByReporter_PkAndTarget_Pk(Long reporterPk, Long targetPk);
}

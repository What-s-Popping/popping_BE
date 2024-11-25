package com.popping.data.report.repository;

import com.popping.data.report.entity.RePopReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RePopReportRepository extends JpaRepository<RePopReport, Long> {
    boolean existsByReporter_PkAndTarget_Pk(Long reporterPk, Long targetPk);
    @Query("select rr.target.pk from RePopReport rr inner join rr.target r " +
            "where rr.reporter.pk = :requesterPk and r.createdAt > (current_timestamp - 1 day) ")
    List<Long> findNotExpiredReportRePopPks(@Param("requesterPk") Long requesterPk);
}

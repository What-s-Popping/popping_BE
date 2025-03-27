package com.popping.data.report.repository;

import com.popping.data.member.entity.Member;
import com.popping.data.report.entity.PopReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PopReportRepository extends JpaRepository<PopReport, Long> {
    boolean existsByReporter_PkAndTarget_Pk(Long reporterPk, Long targetPk);
    @Query("select pr.target.pk from PopReport pr inner join pr.target p " +
            "where pr.reporter.pk = :requesterPk and p.createdAt > (current_timestamp - 1 day) ")
    List<Long> findNotExpiredReportPopPks(@Param("requesterPk") Long requesterPk);

    @Query("select pr.target.pk from PopReport pr inner join pr.target p " +
            "where pr.reporter.pk = :requesterPk " +
                "and p.createdAt > (current_timestamp - 1 day) " +
                "and p.writer in :friends")
    List<Long> findNotExpiredReportPopPks(@Param("requesterPk") Long requesterPk, @Param("friends") List<Member> friends);

    @Modifying
    @Transactional
    @Query("delete from PopReport pr where pr.reporter.pk = :memberPk or pr.target.writer.pk = :memberPk")
    void deleteAllAssociatedMember(@Param("memberPk") Long memberPk);
}

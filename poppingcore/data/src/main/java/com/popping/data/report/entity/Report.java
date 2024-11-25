package com.popping.data.report.entity;

import com.popping.data.member.entity.Member;
import com.popping.data.report.entity.reportType.ReportType;
import com.popping.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member reporter;

    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    public Report(Member reporter, ReportType reportType) {
        this.reporter = reporter;
        this.reportType = reportType;
    }
}

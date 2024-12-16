package com.popping.data.report.entity;

import com.popping.data.member.entity.Member;
import com.popping.data.pop.entity.RePop;
import com.popping.data.report.entity.reportType.ReportType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RePopReport extends Report {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private RePop target;

    @Builder
    public RePopReport(Member reporter, ReportType reportType, RePop target) {
        super(reporter, reportType);
        this.target = target;
    }
}

package com.popping.domain.report.service;

import com.popping.data.member.service.MemberService;
import com.popping.data.pop.service.PopService;
import com.popping.data.pop.service.RePopService;
import com.popping.data.report.entity.PopReport;
import com.popping.data.report.entity.RePopReport;
import com.popping.data.report.service.PopReportService;
import com.popping.data.report.service.RePopReportService;
import com.popping.domain.report.dto.ReportDto;
import com.popping.global.exceptionmessage.ExceptionMessage;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveReportService {
    private final RePopReportService rePopReportService;
    private final PopReportService popReportService;
    private final MemberService memberService;
    private final PopService popService;
    private final RePopService rePopService;

    public void savePopReport(Long targetPk, Long reporterPk, ReportDto.Request reportDto) {
        if (popReportService.isExist(reporterPk, targetPk)) {
            throw new EntityExistsException(ExceptionMessage.REPORT_IS_EXIST.getMessage());
        }

        popReportService.save(
                PopReport.builder()
                        .reporter(memberService.findMember(reporterPk))
                        .target(popService.findPop(targetPk))
                        .reportType(reportDto.getReportType())
                        .build()
        );
    }

    public void saveRePopReport(Long targetPk, Long reporterPk, ReportDto.Request reportDto) {
        if (rePopReportService.isExist(reporterPk, targetPk)) {
            throw new EntityExistsException(ExceptionMessage.REPORT_IS_EXIST.getMessage());
        }

        rePopReportService.save(
                RePopReport.builder()
                        .reporter(memberService.findMember(reporterPk))
                        .target(rePopService.findRePop(targetPk))
                        .reportType(reportDto.getReportType())
                        .build()
        );
    }
}

package com.popping.domain.report.dto;

import com.popping.data.report.entity.reportType.ReportType;
import lombok.Getter;

public class ReportDto {
    @Getter
    public static class Request {
        private ReportType reportType;
    }
}

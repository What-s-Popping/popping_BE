package com.popping.data.report.entity.reportType;

import lombok.Getter;

@Getter
public enum ReportType {
    COMMERCIAL_PROMOTION("상업적/홍보성"),
    ILLEGAL_INFORMATION("불법 정보"),
    PERSONAL_INFORMATION_EXPOSURE("개인정보 노출"),
    SEXUAL_CONTENT("음란/선정성"),
    INSULT_OR_PERSONAL_ATTACK("욕설/인신공격"),
    OTHER("기타");

    private final String description;

    ReportType(String description) {
        this.description = description;
    }
}
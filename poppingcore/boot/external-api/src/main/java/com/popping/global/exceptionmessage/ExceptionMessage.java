package com.popping.global.exceptionmessage;

import lombok.Getter;

@Getter
public enum ExceptionMessage {
    UNSUPPORTED_IMAGE_FORMAT("지원하지 않는 확장자입니다."),
    IMG_NOT_EXIST("파일이 존재하지 않습니다."),
    REPORT_IS_EXIST("신고 이력이 존재합니다.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }
}

package com.popping.global.exceptionmessage;

import lombok.Getter;

@Getter
public enum ExceptionMessage {
    MEMBER_NOT_FOUND("해당 사용자를 찾을 수 없습니다."),
    DISPLAYED_FRAME_NOT_FOUND("현재 조회할 프레임을 찾을 수 없습니다");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }
}

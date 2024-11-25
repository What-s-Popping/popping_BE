package com.popping.global.exceptionmessage;

import lombok.Getter;

@Getter
public enum ExceptionMessage {
    UNSUPPORTED_IMAGE_FORMAT("지원하지 않는 확장자입니다."),
    IMG_NOT_EXIST("파일이 존재하지 않습니다."),
    ALL_POLICIES_NOT_AGREED("약관 동의를 모두 동의하지 않았습니다."),
    ALREADY_SIGN_UP_EMAIL("이미 회원가입한 이메일입니다.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }
}

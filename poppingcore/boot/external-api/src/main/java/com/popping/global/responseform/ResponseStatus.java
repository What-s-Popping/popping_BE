package com.popping.global.responseform;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseStatus {
    private final int httpCode;
    private final HttpStatus httpStatus;
    private final String responseMessage;

    @Builder
    public ResponseStatus(int httpCode, HttpStatus httpStatus, String responseMessage) {
        this.httpCode = httpCode;
        this.httpStatus = httpStatus;
        this.responseMessage = responseMessage;
    }
}
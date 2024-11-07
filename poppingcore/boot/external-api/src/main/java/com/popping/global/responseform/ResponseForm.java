package com.popping.global.responseform;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseForm <T> {
    private final ResponseStatus responseStatus;
    private final T content;

    @Builder
    public ResponseForm(T content, HttpStatus httpStatus, String responseMessage) {
        this.content = content;
        this.responseStatus = ResponseStatus.builder()
                .httpStatus(httpStatus)
                .httpCode(httpStatus.value())
                .responseMessage(responseMessage)
                .build();
    }
}


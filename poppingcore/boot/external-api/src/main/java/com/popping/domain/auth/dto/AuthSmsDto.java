package com.popping.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

public class AuthSmsDto {
    @Getter
    public static class VerifyCodeRequest {
        @NotBlank
        private final String phoneNumber;
        @NotBlank
        private final String code;

        @Builder
        public VerifyCodeRequest(String phoneNumber, String code) {
            this.phoneNumber = phoneNumber;
            this.code = code;
        }
    }

    @Getter
    public static class VerifyCodeResponse {
        private final String alreadySignUpPlatform;
        private final boolean isValidCode;

        @Builder
        public VerifyCodeResponse(String alreadySignUpPlatform, boolean isValidCode) {
            this.alreadySignUpPlatform = alreadySignUpPlatform;
            this.isValidCode = isValidCode;
        }
    }
}

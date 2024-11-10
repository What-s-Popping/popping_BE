package com.popping.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

public class TokenDto {
    @Getter
    public static class Token {
        private final String token;
        private final Date expiredTime;

        @Builder
        public Token(String token, Date expiredTime) {
            this.expiredTime = expiredTime;
            this.token = token;
        }
    }

    @Getter
    public static class Tokens {
        private final Token refreshToken;
        private final Token accessToken;

        @Builder
        public Tokens(Token accessToken, Token refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }

    @Getter
    public static class TokenResponse {
        private Tokens tokens;

        @Builder
        public TokenResponse(Tokens tokens) {
            this.tokens = tokens;
        }
    }
}

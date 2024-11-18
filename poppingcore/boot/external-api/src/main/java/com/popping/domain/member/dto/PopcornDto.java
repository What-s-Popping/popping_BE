package com.popping.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

public class PopcornDto {
    @Getter
    public static class Response{
        @Builder
        public Response(int popcornBalance) {
            this.popcornBalance = popcornBalance;
        }

        private final int popcornBalance;
    }

}

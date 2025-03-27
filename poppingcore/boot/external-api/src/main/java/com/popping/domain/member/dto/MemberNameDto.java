package com.popping.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

public class MemberNameDto {
    @Getter
    public static class Response {
        private String name;

        @Builder
        public Response(String name) {
            this.name = name;
        }
    }
}

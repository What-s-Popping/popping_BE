package com.popping.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

public class MemberInfoDto {
    @Getter
    public static class Response {
        private final String name;
        private final String profileImgUrl;

        @Builder
        public Response(String name, String profileImgUrl) {
            this.name = name;
            this.profileImgUrl = profileImgUrl;
        }
    }
}

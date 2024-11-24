package com.popping.domain.member.dto;

import lombok.*;

public class ProfileDto {
    @Getter
    public static class Response{
        private final String profileImgUploadUrl;
        private final String imgName;
        @Builder
        public Response(String profileImgUploadUrl, String imgName) {
            this.profileImgUploadUrl = profileImgUploadUrl;
            this.imgName = imgName;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Request{
        private String updateName;
    }
}

package com.popping.domain.img.dto;

import lombok.Builder;
import lombok.Getter;

public class ImgDto {
    @Getter
    public static class Response{
        private String imgName;
        private String presignedUrl;

        @Builder
        public Response(String imgName, String presignedUrl) {
            this.imgName = imgName;
            this.presignedUrl = presignedUrl;
        }
    }
}

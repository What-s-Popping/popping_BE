package com.popping.domain.frame.dto;

import lombok.Builder;
import lombok.Getter;

public class FrameDto {
    @Getter
    public static class Response {
        private final String key;
        private final String title;

        @Builder
        public Response(String key, String title) {
            this.key = key;
            this.title = title;
        }
    }
}

package com.popping.domain.member.dto;

import com.popping.data.post.chip.ColorChip;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class PopDto {
    @Getter
    public static class Response {
        private final LocalDateTime createAt;
        private final String content;
        private final String imgUrl;
        private final ColorChip colorChip;

        @Builder
        public Response(LocalDateTime createAt, String content, String imgUrl, ColorChip colorChip) {
            this.createAt = createAt;
            this.content = content;
            this.imgUrl = imgUrl;
            this.colorChip = colorChip;
        }
    }
}

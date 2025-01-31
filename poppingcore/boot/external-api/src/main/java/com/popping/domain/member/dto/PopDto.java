package com.popping.domain.member.dto;

import com.popping.data.pop.chip.ColorChip;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class PopDto {
    @Getter
    public static class Response {
        private final long popId;
        private final LocalDateTime createAt;
        private final String content;
        private final String imgUrl;
        private final ColorChip colorChip;

        @Builder
        public Response(long popId, LocalDateTime createAt, String content, String imgUrl, ColorChip colorChip) {
            this.popId = popId;
            this.createAt = createAt;
            this.content = content;
            this.imgUrl = imgUrl;
            this.colorChip = colorChip;
        }
    }
}

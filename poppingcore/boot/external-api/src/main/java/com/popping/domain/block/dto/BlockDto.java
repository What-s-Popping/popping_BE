package com.popping.domain.block.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class BlockDto {
    @Getter
    @Setter
    public static class Response {
        @JsonProperty("isBlocked")
        private boolean isBlocked;

        @Builder
        public Response(boolean isBlocked) {
            this.isBlocked = isBlocked;
        }
    }
}

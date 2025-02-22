package com.popping.domain.notification.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class NotificationStatusDto {
    @Getter
    @Setter
    public static class Response {
        @JsonProperty("isAllowNotify")
        private boolean isAllowNotify;

        @Builder
        public Response(boolean isAllowNotify) {
            this.isAllowNotify = isAllowNotify;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Request {
        @JsonProperty("isAllowNotify")
        private boolean isAllowNotify;

        @Builder
        public Request(boolean isAllowNotify) {
            this.isAllowNotify = isAllowNotify;
        }
    }
}

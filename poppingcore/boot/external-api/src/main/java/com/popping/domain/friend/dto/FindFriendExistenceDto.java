package com.popping.domain.friend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class FindFriendExistenceDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Response {
        @JsonProperty("isExist")
        private boolean isExist;

        @Builder
        public Response(boolean isExist) {
            this.isExist = isExist;
        }
    }
}

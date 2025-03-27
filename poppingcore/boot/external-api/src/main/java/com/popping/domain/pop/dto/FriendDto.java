package com.popping.domain.pop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class FriendDto {
    @Getter
    public static class Response {
        private String nickname;
        private String profileImgUrl;
        private List<FriendPop> friendPops;

        @Builder
        public Response(String nickname, String profileImgUrl) {
            this.friendPops = new ArrayList<>();
            this.nickname = nickname;
            this.profileImgUrl = profileImgUrl;
        }
    }

    public static class FriendPop {
        @Getter
        @Setter
        private Long popId;
        @JsonProperty("isRead")
        private boolean isRead;

        @Builder
        public FriendPop(boolean isRead, Long popId) {
            this.isRead = isRead;
            this.popId = popId;
        }
    }
}
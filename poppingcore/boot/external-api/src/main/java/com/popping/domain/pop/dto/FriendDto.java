package com.popping.domain.pop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class FriendDto {
    @Getter
    @Setter
    public static class Response {
        private Long id;
        @JsonProperty("isRead")
        private boolean isRead;
        private String nickname;
        private String profileImgUrl;
        private List<Long> friendPopIds;

        @Builder
        public Response(Long id, List<Long> friendPopIds, boolean isRead, String nickname, String profileImgUrl) {
            this.id = id;
            this.friendPopIds = friendPopIds;
            this.isRead = isRead;
            this.nickname = nickname;
            this.profileImgUrl = profileImgUrl;
        }
    }
}

package com.popping.domain.friend.dto;

import com.popping.data.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

public class FindFriendContactDto {
    @Getter
    public static class Response {
        private Long id;
        private String name;
        private String profileImgUrl;

        @Builder
        public Response(Member member, String profileImgUrl) {
            this.id = member.getPk();
            this.name = member.getName();
            this.profileImgUrl = profileImgUrl;
        }
    }
}

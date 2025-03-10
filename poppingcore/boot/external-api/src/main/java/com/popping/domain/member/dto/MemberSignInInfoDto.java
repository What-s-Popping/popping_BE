package com.popping.domain.member.dto;

import com.popping.data.member.entity.Member;
import com.popping.data.member.entity.signupplatform.SignUpPlatform;
import lombok.Builder;
import lombok.Getter;

public class MemberSignInInfoDto {
    @Getter
    public static class Response {
        private String email;
        private SignUpPlatform singInPlatform;

        @Builder
        public Response(Member requester) {
            this.email = requester.getEmail();
            this.singInPlatform = requester.getSignUpPlatform();
        }
    }
}

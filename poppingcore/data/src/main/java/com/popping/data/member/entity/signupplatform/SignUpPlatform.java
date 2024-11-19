package com.popping.data.member.entity.signupplatform;

import lombok.Getter;

@Getter
public enum SignUpPlatform {
    KAKAO("카카오"),
    NAVER("네이버"),
    GOOGLE("구글"),
    APPLE("애플");

    private final String name;

    SignUpPlatform(String name) {
        this.name = name;
    }
}
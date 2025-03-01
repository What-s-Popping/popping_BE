package com.popping.domain.friend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.popping.data.share.entity.Shared;
import com.popping.data.share.entity.SharedPlatform;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FriendRequestDto {
    private final String name;
    private final SharedPlatform platform;
    private final String profileUrl;
    private final Long userId;

    @Builder(access = AccessLevel.PRIVATE)
    private FriendRequestDto(String name, SharedPlatform platform, String profileUrl, Long userId, boolean isBlock) {
        this.name = name;
        this.platform = platform;
        this.profileUrl = profileUrl;
        this.userId = userId;
    }

    public static FriendRequestDto of(Shared shared,String profileUrl) {
        return FriendRequestDto.builder()
                .name(shared.getSharedMember().getName())
                .platform(shared.getSharedPlatform())
                .userId(shared.getSharedMember().getPk())
                .profileUrl(profileUrl)
                .build();
    }
}

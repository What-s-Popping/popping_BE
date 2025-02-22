package com.popping.domain.friend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FriendRecommendationDto {
    private final Long id;
    private final String profileImgUrl;
    private final String name;

    @Builder
    public FriendRecommendationDto(Long id, String profileImgUrl, String name) {
        this.id = id;
        this.profileImgUrl = profileImgUrl;
        this.name = name;
    }
}

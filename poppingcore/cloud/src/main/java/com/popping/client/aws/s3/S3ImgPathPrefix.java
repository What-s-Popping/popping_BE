package com.popping.client.aws.s3;

import lombok.Getter;

@Getter
public enum S3ImgPathPrefix {
    PROFILE("profile/"),
    POP("pop/"),
    RE_POP("re-pop/"),
    CUSTOM_FRIEND_GROUP("custom-friend-group/");

    private final String pathPrefix;
    S3ImgPathPrefix(String pathPrefix) {
        this.pathPrefix = pathPrefix;
    }
}

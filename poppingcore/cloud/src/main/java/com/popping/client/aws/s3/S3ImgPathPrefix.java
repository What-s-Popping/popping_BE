package com.popping.client.aws.s3;

import lombok.Getter;

@Getter
public enum S3ImgPathPrefix {
    PROFILE("profile/"),
    POP("pop/");

    private final String pathPrefix;
    S3ImgPathPrefix(String pathPrefix) {
        this.pathPrefix = pathPrefix;
    }
}

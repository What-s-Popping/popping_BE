package com.popping.domain.img;

import lombok.Getter;

@Getter
public enum ImgType {
    PROFILE("profile/"),
    POP("pop/"),
    RE_POP("re-pop/");

    private final String s3Path;

    ImgType(String s3Path) {
        this.s3Path = s3Path;
    }
}

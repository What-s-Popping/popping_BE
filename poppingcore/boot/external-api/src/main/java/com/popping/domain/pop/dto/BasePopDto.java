package com.popping.domain.pop.dto;

import com.popping.data.pop.chip.ColorChip;
import com.popping.domain.pop.dto.poptype.PopType;
import lombok.Getter;

@Getter
public class BasePopDto {
    private final Long id;
    private final String nickname;
    private final String profileImgUrl;
    private final String popImgUrl;
    private final String chip;
    private final ColorChip colorChip;
    private final String contents;
    private final PopType popType;

    public BasePopDto(Long id, String chip, String nickname, String profileImgUrl, String popImgUrl, ColorChip colorChip, String contents, PopType popType) {
        this.id = id;
        this.chip = chip;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.popImgUrl = popImgUrl;
        this.colorChip = colorChip;
        this.contents = contents;
        this.popType = popType;
    }
}

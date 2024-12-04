package com.popping.domain.pop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.popping.data.pop.chip.ColorChip;
import com.popping.data.pop.emotion.ActionState;
import com.popping.domain.pop.dto.poptype.PopType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class BasePopDetailDto {
    private Long writerId;
    private String nickname;
    private String profileImgUrl;

    private Long popId;
    private String popImgUrl;
    private String chip;
    private ColorChip colorChip;
    private String contents;
    private PopType popType;
    private ActionState actionState;
    @JsonProperty("isWriter")
    private boolean isWriter;
    private LocalDateTime createAt;

    public BasePopDetailDto(ActionState actionState, String chip, ColorChip colorChip, String contents, LocalDateTime createAt, boolean isPrivateProfile, boolean isWriter, String nickname, Long popId, String popImgUrl, PopType popType, String profileImgUrl, Long writerId) {
        this.actionState = actionState;
        this.chip = chip;
        this.colorChip = colorChip;
        this.contents = contents;
        this.createAt = createAt;
        this.isWriter = isWriter;
        this.nickname = isPrivateProfile ? null : nickname;
        this.popId = popId;
        this.popImgUrl = popImgUrl;
        this.popType = popType;
        this.profileImgUrl = profileImgUrl;
        this.writerId = writerId;
    }
}

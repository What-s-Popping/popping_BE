package com.popping.domain.pop.dto;

import com.popping.data.pop.chip.ColorChip;
import com.popping.data.pop.emotion.ActionState;
import com.popping.domain.pop.dto.poptype.PopType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class RePopDetailDto {

    @Getter
    @Setter
    public static class Response extends BasePopDetailDto {
        @Builder
        public Response(ActionState actionState, String chip, ColorChip colorChip, String contents, LocalDateTime createAt, boolean isPrivateProfile, boolean isWriter, String nickname, Long popId, String popImgUrl, String profileImgUrl, Long writerId) {
            super(actionState, chip, colorChip, contents, createAt, isPrivateProfile, isWriter, nickname, popId, popImgUrl, PopType.RE_POP, profileImgUrl, writerId);
        }
    }
}

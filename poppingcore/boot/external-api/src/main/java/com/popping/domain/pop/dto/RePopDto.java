package com.popping.domain.pop.dto;

import com.popping.data.pop.chip.ColorChip;
import com.popping.domain.pop.dto.poptype.PopType;
import lombok.Builder;
import lombok.Getter;

public class RePopDto {
    @Getter
    public static class Response extends BasePopDto{
        @Builder
        public Response(Long id, String chip, String nickname, String profileImgUrl, String popImgUrl, ColorChip colorChip, String contents) {
            super(id, chip, nickname, profileImgUrl, popImgUrl, colorChip, contents, PopType.RE_POP);
        }
    }
}

package com.popping.domain.pop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.popping.data.pop.chip.ColorChip;
import com.popping.domain.pop.dto.poptype.PopType;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

public class RePopDto {
    @Getter
    public static class Request {
        @JsonProperty(value = "isPrivateProfile")
        private boolean isPrivateProfile;
        @NotBlank
        private String chip;
        @NotBlank
        private String contents;
        private ColorChip colorChip;
        private Long targetMember;
        private String imgName;

        public boolean isExistImg() {
            return imgName != null && !imgName.isBlank();
        }
    }

    @Getter
    public static class Response extends BasePopDto{
        @Builder
        public Response(Long id, String chip, String nickname, String profileImgUrl, String popImgUrl, ColorChip colorChip, String contents) {
            super(id, chip, nickname, profileImgUrl, popImgUrl, colorChip, contents, PopType.RE_POP);
        }
    }
}

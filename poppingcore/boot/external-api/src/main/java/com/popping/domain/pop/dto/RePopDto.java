package com.popping.domain.pop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.popping.data.member.entity.Member;
import com.popping.data.pop.chip.ColorChip;
import com.popping.data.pop.entity.RePop;
import com.popping.domain.pop.dto.poptype.PopType;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

public class RePopDto {
    @Getter
    public static class Request {
        @JsonProperty(value = "isPrivateProfile")
        private boolean isPrivateProfile;
        @JsonProperty(value = "isWidgetAllow")
        private boolean isWidgetAllow;
        @NotBlank
        private String chip;
        @NotBlank
        private String contents;
        private ColorChip colorChip;
        private PopType targetPopType;
        private Long targetMemberId;
        private Long targetPopId;
        private String imgName;

        public boolean isExistImg() {
            return imgName != null && !imgName.isBlank();
        }

        public RePop of(Member writer, Member targetMember) {
            return RePop.builder()
                    .isPrivateProfile(isPrivateProfile)
                    .isWidgetAllow(isWidgetAllow)
                    .chip(chip)
                    .colorChip(colorChip)
                    .writer(writer)
                    .targetMember(targetMember)
                    .imgName(imgName)
                    .contents(contents)
                    .build();
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

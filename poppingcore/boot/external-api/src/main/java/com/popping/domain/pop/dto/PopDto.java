package com.popping.domain.pop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.popping.data.pop.chip.ColorChip;
import com.popping.domain.pop.dto.poptype.PopType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class PopDto {
    @Getter
    public static class Request{
        @JsonProperty(value = "isPrivateProfile")
        private boolean isPrivateProfile;
        @JsonProperty(value = "isGlobalShare")
        private boolean isGlobalShare;
        private String chip;
        private ColorChip colorChip;
        private List<Long> targetMemberList;
        private String contents;
        private String imgName;

        @Override
        public String toString() {
            return "Request{" +
                    "isPrivateProfile=" + isPrivateProfile +
                    ", isGlobalShare=" + isGlobalShare +
                    ", chip='" + chip + '\'' +
                    ", colorChip=" + colorChip +
                    ", targetMemberList=" + targetMemberList +
                    ", contents='" + contents + '\'' +
                    ", imgName='" + imgName + '\'' +
                    '}';
        }
    }

    @Getter
    public static class Response extends BasePopDto{
        @Builder
        public Response(Long id, String chip, String nickname, String profileImgUrl, String popImgUrl, ColorChip colorChip, String contents) {
            super(id, chip, nickname, profileImgUrl, popImgUrl, colorChip, contents, PopType.POP);
        }
    }
}

package com.popping.domain.pop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.popping.data.post.chip.ColorChip;
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
    }
}

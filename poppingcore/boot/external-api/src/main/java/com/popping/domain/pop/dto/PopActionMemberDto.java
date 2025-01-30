package com.popping.domain.pop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.popping.data.pop.emotion.ActionState;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PopActionMemberDto {
    @Getter
    public static class Response {
        List<MemberInfo> memberInfos;
        int totalActionMemberCnt;

        @Builder
        public Response(List<MemberInfo> memberInfos) {
            this.memberInfos = memberInfos;
            this.totalActionMemberCnt = memberInfos.size();
        }
    }

    @Getter
    @Setter
    public static class MemberInfo {
        Long memberId;
        String memberName;
        String memberProfileImgUrl;
        @JsonProperty(value = "isShared")
        boolean isShared;
        @JsonProperty(value = "isSaved")
        boolean isSaved;
        @JsonProperty(value = "isRepop")
        boolean isRepop;
        ActionState actionState;

        @Builder
        public MemberInfo(Long memberId, String memberName, String memberProfileImgUrl, boolean isShared, boolean isSaved, boolean isRepop, ActionState actionState) {
            this.memberId = memberId;
            this.memberName = memberName;
            this.memberProfileImgUrl = memberProfileImgUrl;
            this.isShared = isShared;
            this.isSaved = isSaved;
            this.isRepop = isRepop;
            this.actionState = actionState;
        }
    }
}

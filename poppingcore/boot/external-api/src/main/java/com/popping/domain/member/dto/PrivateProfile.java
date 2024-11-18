package com.popping.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

public class PrivateProfile {
    @Getter
    public static class QuotaResponse {
        private final int privateProfileQuota;

        @Builder
        public QuotaResponse(int privateProfileQuota) {
            this.privateProfileQuota = privateProfileQuota;
        }
    }

    @Getter
    public static class PrivatePostCntResponse {
        private final int privateProfilePostCnt;

        @Builder
        public PrivatePostCntResponse(int privateProfilePostCnt) {
            this.privateProfilePostCnt = privateProfilePostCnt;
        }
    }
}

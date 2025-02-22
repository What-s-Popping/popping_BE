package com.popping.domain.notification.fcmtype;

import lombok.Getter;

@Getter
public enum NotificationType {
    POP("님의 팝핑이 업로드되었습니다."),
    RE_POP("님이 리팝을 보냈습니다."),
    FRIEND_REQUEST("님이 친구 신청을 수락했습니다."),
    FRIEND_REQUEST_APPROVE("님이 친구 신청을 보냈습니다.");

    private String fcmSuffix;

    NotificationType(String fcmSuffix) {
        this.fcmSuffix = fcmSuffix;
    }

    public boolean isNotMulticastFCMEvent() {
        return this != POP;
    }
}

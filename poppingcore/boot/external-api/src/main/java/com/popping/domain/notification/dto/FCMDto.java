package com.popping.domain.notification.dto;

import com.popping.domain.notification.fcmtype.NotificationType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class FCMDto {
    @Getter
    public static class FCM {
        private final String requesterNickname;
        private final NotificationType notificationType;

        public FCM(NotificationType notificationType, String requesterNickname) {
            this.notificationType = notificationType;
            this.requesterNickname = requesterNickname;
        }
    }

    @Getter
    public static class FCMEvent extends FCM{
        private final String targetFcmToken;

        @Builder
        public FCMEvent(NotificationType notificationType, String requesterNickname, String targetFcmToken) {
            super(notificationType, requesterNickname);
            this.targetFcmToken = targetFcmToken;
        }

        public MsgDto from() {
            return MsgDto.builder()
                    .requesterNickname(getRequesterNickname())
                    .notificationType(getNotificationType())
                    .targetFcmToken(getTargetFcmToken())
                    .build();
        }
    }

    @Getter
    public static class MulticastFCMEvent extends FCM{
        private final List<String> targetFcmTokens;

        @Builder
        public MulticastFCMEvent(NotificationType notificationType, String requesterNickname, List<String> targetFcmTokens) {
            super(notificationType, requesterNickname);
            this.targetFcmTokens = targetFcmTokens;
        }

        public MulticastMsgDto from() {
            return MulticastMsgDto.builder()
                    .requesterNickname(getRequesterNickname())
                    .notificationType(getNotificationType())
                    .targetFcmTokens(targetFcmTokens)
                    .build();
        }
    }

    @Getter
    public static class MsgDto extends FCM{
        private final String targetFcmToken;

        @Builder
        public MsgDto(NotificationType notificationType, String requesterNickname, String targetFcmToken) {
            super(notificationType, requesterNickname);
            this.targetFcmToken = targetFcmToken;
        }
    }

    @Getter
    public static class MulticastMsgDto extends FCM{
        private final List<String> targetFcmTokens;

        @Builder
        public MulticastMsgDto(NotificationType notificationType, String requesterNickname, List<String> targetFcmTokens) {
            super(notificationType, requesterNickname);
            this.targetFcmTokens = targetFcmTokens;
        }
    }
}

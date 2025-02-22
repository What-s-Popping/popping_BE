package com.popping.domain.notification.service;

import com.google.firebase.messaging.*;
import com.popping.domain.notification.dto.FCMDto;
import com.popping.domain.notification.fcmtype.NotificationType;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
class FCMMsgGenerator {
    private static final String TITLE = "Popping";

    public Message generateMsg(FCMDto.MsgDto msgDto) {
        HashMap<String, String> data = generateFcmData(msgDto.getNotificationType());

        return Message.builder()
                .setNotification(
                        Notification.builder()
                                .setTitle(TITLE)
                                .setBody(generateMsgBody(msgDto.getRequesterNickname(), msgDto.getNotificationType()))
                                .build()
                )
                .putAllData(data)
                .setToken(msgDto.getTargetFcmToken())
                .build();
    }

    public MulticastMessage generateMulticastMsg(FCMDto.MulticastMsgDto msgDto) {
        HashMap<String, String> data = generateFcmData(msgDto.getNotificationType());

        return MulticastMessage.builder()
                .setNotification(
                        Notification.builder()
                                .setTitle(TITLE)
                                .setBody(generateMsgBody(msgDto.getRequesterNickname(), msgDto.getNotificationType()))
                                .build()
                )
                .putAllData(data)
                .addAllTokens(msgDto.getTargetFcmTokens())
                .build();
    }

    private static HashMap<String, String> generateFcmData(NotificationType notificationType) {
        HashMap<String, String> data = new HashMap<>();
        data.put("notificationType", notificationType.name());
        return data;
    }

    private static String generateMsgBody(String requesterName, NotificationType notificationType) {
        return requesterName + notificationType.getFcmSuffix();
    }
}
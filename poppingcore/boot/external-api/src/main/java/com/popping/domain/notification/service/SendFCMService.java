package com.popping.domain.notification.service;

import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.popping.domain.notification.dto.FCMDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class SendFCMService {
    private final FCMMsgGenerator fcmMsgGenerator;
    private final FCMSender fcmSender;

    public void sendPopMsg(FCMDto.MulticastMsgDto msgDto) {
        MulticastMessage message = fcmMsgGenerator.generateMulticastMsg(msgDto);
        fcmSender.send(message);
    }

    public void sendRePopMsg(FCMDto.MsgDto msgDto) {
        Message message = fcmMsgGenerator.generateMsg(msgDto);
        fcmSender.send(message);
    }

    public void sendFriendRequestMsg(FCMDto.MsgDto msgDto) {
        Message message = fcmMsgGenerator.generateMsg(msgDto);
        fcmSender.send(message);
    }

    public void sendFriendRequestApproveMsg(FCMDto.MsgDto msgDto) {
        Message message = fcmMsgGenerator.generateMsg(msgDto);
        fcmSender.send(message);
    }
}

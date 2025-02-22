package com.popping.domain.notification.service;

import com.popping.domain.notification.dto.FCMDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class FCMEvent {
    private final SendFCMService sendFCMService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void sendFCM(FCMDto.FCMEvent event) {
        FCMDto.MsgDto msgDto = event.from();
        switch (event.getNotificationType()) {
            case RE_POP -> sendFCMService.sendRePopMsg(msgDto);
            case FRIEND_REQUEST -> sendFCMService.sendFriendRequestMsg(msgDto);
            case FRIEND_REQUEST_APPROVE -> sendFCMService.sendFriendRequestApproveMsg(msgDto);
            default -> throw new IllegalArgumentException("지원하지 않는 알림 타입입니다.");
        }
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void sendFCMs(FCMDto.MulticastFCMEvent event) {
        FCMDto.MulticastMsgDto msgDto = event.from();
        if (event.getNotificationType().isNotMulticastFCMEvent()) {
            throw new IllegalArgumentException("지원하지 않는 알림 타입입니다.");
        }

        sendFCMService.sendPopMsg(msgDto);
    }
}

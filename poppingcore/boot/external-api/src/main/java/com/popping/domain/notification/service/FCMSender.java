package com.popping.domain.notification.service;

import com.google.firebase.messaging.*;
import com.popping.domain.notification.exception.FcmServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class FCMSender {
    @Retryable(retryFor = FcmServerException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2.0))
    public void send(Message message) {
        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            validateRetrySendFCM(e.getMessagingErrorCode());
        }
    }

    public void send(MulticastMessage message) {
        try {
            FirebaseMessaging.getInstance().sendEachForMulticast(message);
        } catch (FirebaseMessagingException e) {
            log.info(e.getMessage());
        }
    }

    private void validateRetrySendFCM(MessagingErrorCode msgErrorCode) {
        if (isRetryErrorCode(msgErrorCode)) {
            throw new FcmServerException();
        }
    }

    private boolean isRetryErrorCode(MessagingErrorCode msgErrorCode) {
        return msgErrorCode.equals(MessagingErrorCode.INTERNAL) || msgErrorCode.equals(MessagingErrorCode.UNAVAILABLE);
    }
}

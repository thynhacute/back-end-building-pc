package com.webapp.buildPC.service.impl;


import com.webapp.buildPC.domain.Transaction.PushNotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PushNotificationService {

    private FCMService fcmService;

    public PushNotificationService(FCMService fcmService) {
        this.fcmService = fcmService;
    }


    public void sendPushNotificationToToken(PushNotificationRequest request) {
        try {
            fcmService.sendMessageToToken(request);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}

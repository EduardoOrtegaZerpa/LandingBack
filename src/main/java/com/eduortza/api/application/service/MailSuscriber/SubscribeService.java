package com.eduortza.api.application.service.MailSuscriber;

import com.eduortza.api.application.exception.StoreException;
import com.eduortza.api.application.port.in.MailSubscriber.subscribe.SubscribePort;
import com.eduortza.api.application.port.out.MailSuscriber.StoreMailSubscriberPort;
import com.eduortza.api.common.UseCase;
import com.eduortza.api.domain.MailSuscriber;

@UseCase
public class SubscribeService implements SubscribePort {

    private final StoreMailSubscriberPort storeMailSubscriberPort;

    public SubscribeService(StoreMailSubscriberPort storeMailSubscriberPort) {
        this.storeMailSubscriberPort = storeMailSubscriberPort;
    }

    @Override
    public void subscribe(String email) {
        MailSuscriber mailSuscriber = new MailSuscriber();

        mailSuscriber.setEmail(email);

        try {
            storeMailSubscriberPort.store(mailSuscriber);
        } catch (Exception e) {
            throw new StoreException("Error while trying to store in Database", e);
        }
    }
}

package com.eduortza.api.application.port.out.MailSuscriber;

import com.eduortza.api.domain.MailSuscriber;

public interface StoreMailSubscriberPort {
    void store(MailSuscriber mailSuscriber) throws Exception;
}
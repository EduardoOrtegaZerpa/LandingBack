package com.eduortza.api.application.port.out.MailSuscriber;

import com.eduortza.api.application.exception.LoadingException;
import com.eduortza.api.domain.MailSuscriber;

import java.util.List;

public interface GetMailSubscriberPort {
    MailSuscriber getMailSuscriber(String email);
    List<MailSuscriber> getAllMailSubscriber();
}

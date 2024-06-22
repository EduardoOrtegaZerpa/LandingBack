package com.eduortza.api.application.port.out;

import com.eduortza.api.domain.MailSuscriber;

import java.util.List;

public interface MailPort {
    void sendMail(String from, String subject, String body);
    void sendMailToAllSubscribers(List<MailSuscriber> toList, String subject, String body);
}

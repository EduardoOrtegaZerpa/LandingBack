package com.eduortza.api.application.port.out;

import com.eduortza.api.domain.MailSuscriber;

import java.util.List;

public interface MailPort {
    void sendMail(String from, String subject, String body) throws Exception;
    void sendMailToAllSubscribers(List<MailSuscriber> toList, String subject) throws Exception;
}

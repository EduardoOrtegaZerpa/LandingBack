package com.eduortza.api.application.port.out;

import com.eduortza.api.application.exception.MailException;
import com.eduortza.api.domain.BlogPost;
import com.eduortza.api.domain.MailSuscriber;

import java.util.List;

public interface MailPort {
    void sendMail(String from, String subject, String body);
    void sendMailToBlogSubscribers(List<MailSuscriber> toList, BlogPost blogPost);
}

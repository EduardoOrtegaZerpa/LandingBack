package com.eduortza.api.application.port.out;

public interface MailPort {
    void sendMail(String from, String subject, String body);
}

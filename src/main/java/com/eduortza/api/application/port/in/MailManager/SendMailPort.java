package com.eduortza.api.application.port.in.MailManager;

public interface SendMailPort {
    void sendMail(SendMailCommand command);
    void sendMailToAllSubscribers(SendMailToAllSuscribersCommand command);
}

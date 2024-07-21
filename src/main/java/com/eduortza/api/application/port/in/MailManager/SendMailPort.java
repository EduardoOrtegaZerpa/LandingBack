package com.eduortza.api.application.port.in.MailManager;

public interface SendMailPort {
    void sendMail(SendMailCommand command) throws Exception;
}

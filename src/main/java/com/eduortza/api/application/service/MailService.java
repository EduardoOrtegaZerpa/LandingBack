package com.eduortza.api.application.service;

import com.eduortza.api.application.port.in.MailManager.SendMailCommand;
import com.eduortza.api.application.port.in.MailManager.SendMailPort;
import com.eduortza.api.application.port.out.MailPort;

public class MailService implements SendMailPort {

    private final MailPort mailPort;

    public MailService(MailPort mailPort) {
        this.mailPort = mailPort;
    }

    @Override
    public void sendMail(SendMailCommand command) {
        mailPort.sendMail(command.getFrom(), command.getSubject(), command.getText());
    }
}

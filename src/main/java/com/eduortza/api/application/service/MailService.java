package com.eduortza.api.application.service;

import com.eduortza.api.application.port.in.MailManager.SendMailCommand;
import com.eduortza.api.application.port.in.MailManager.SendMailPort;
import com.eduortza.api.application.port.in.MailManager.SendMailToAllSuscribersCommand;
import com.eduortza.api.application.port.out.MailPort;
import com.eduortza.api.domain.MailSuscriber;

import java.util.List;

public class MailService implements SendMailPort {

    private final MailPort mailPort;

    public MailService(MailPort mailPort) {
        this.mailPort = mailPort;
    }

    @Override
    public void sendMail(SendMailCommand command) {
        mailPort.sendMail(command.getFrom(), command.getSubject(), command.getText());
    }

    @Override
    public void sendMailToAllSubscribers(SendMailToAllSuscribersCommand command) {
        List<MailSuscriber> mailSuscribers = command.getToList();

        mailPort.sendMailToAllSubscribers(mailSuscribers, command.getSubject(), command.getText());

    }

}

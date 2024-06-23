package com.eduortza.api.application.service;

import com.eduortza.api.application.exception.MailException;
import com.eduortza.api.application.port.in.MailManager.SendMailCommand;
import com.eduortza.api.application.port.in.MailManager.SendMailPort;
import com.eduortza.api.application.port.in.MailManager.SendMailToAllSuscribersCommand;
import com.eduortza.api.application.port.out.MailPort;
import com.eduortza.api.common.UseCase;
import com.eduortza.api.domain.MailSuscriber;

import java.util.List;

@UseCase
public class MailService implements SendMailPort {

    private final MailPort mailPort;

    public MailService(MailPort mailPort) {
        this.mailPort = mailPort;
    }

    @Override
    public void sendMail(SendMailCommand command) {
        try {
            mailPort.sendMail(command.getFrom(), command.getSubject(), command.getText());
        } catch (Exception e) {
            throw new MailException("Error sending mail", e);
        }
    }

    @Override
    public void sendMailToAllSubscribers(SendMailToAllSuscribersCommand command) {
        List<MailSuscriber> mailSubscribers = command.getToList();

        try {
            mailPort.sendMailToAllSubscribers(mailSubscribers, command.getSubject());
        } catch (Exception e) {
            throw new MailException("Error sending mail to all subscribers", e);
        }

    }

}

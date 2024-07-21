package com.eduortza.api.application.service;

import com.eduortza.api.adapter.exception.AlreadyExistsException;
import com.eduortza.api.application.exception.MailException;
import com.eduortza.api.application.port.in.MailManager.SendMailCommand;
import com.eduortza.api.application.port.in.MailManager.SendMailPort;
import com.eduortza.api.application.port.out.MailPort;
import com.eduortza.api.common.UseCase;

@UseCase
public class MailService implements SendMailPort {

    private final MailPort mailPort;

    public MailService(MailPort mailPort) {
        this.mailPort = mailPort;
    }

    @Override
    public void sendMail(SendMailCommand command) {
        try {
            String subject = command.getSubject() + " | from " + command.getName();
            mailPort.sendMail(command.getFrom(), command.getSubject(), command.getText());
        } catch (AlreadyExistsException e) {
            throw new AlreadyExistsException("Mail already exists", e);
        }
        catch (Exception e) {
            throw new MailException("Error sending mail", e);
        }
    }

}

package com.eduortza.api.adapter;

import com.eduortza.api.application.port.out.MailPort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Repository;
import org.springframework.mail.javamail.JavaMailSender;


@Repository
public class MailSystemAdapter implements MailPort {

    private final JavaMailSender javaMailSender;

    @Autowired
    public MailSystemAdapter(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMail(String from, String subject, String body) {
        // Send mail
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(from);
        mail.setTo("eduardo@localhost");
        mail.setSubject(subject);
        mail.setText(body);

        javaMailSender.send(mail);


    }
}

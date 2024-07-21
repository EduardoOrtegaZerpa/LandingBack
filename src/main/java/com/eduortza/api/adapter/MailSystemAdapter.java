package com.eduortza.api.adapter;

import com.eduortza.api.application.port.out.MailPort;

import com.eduortza.api.domain.MailSuscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Repository;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;


@Repository
public class MailSystemAdapter implements MailPort {

    private final JavaMailSender javaMailSender;

    @Autowired
    public MailSystemAdapter(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMail(String from, String subject, String body) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(from);
        mail.setTo("eduardo@localhost");
        mail.setSubject(subject);
        mail.setText(body);

//        javaMailSender.send(mail);
    }

    public void sendMailTo(String to, String subject, String token) {
        SimpleMailMessage mail = new SimpleMailMessage();
        String body = generateHtmlBodyWithToken(token);

        mail.setFrom("noreply@localhost");
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(body);

        //javaMailSender.send(mail);

    }


    String generateHtmlBodyWithToken(String token){
        String htmlBody = "<h1>Click the link to unsn</h1><a href='http://eduortza.com:8080/subscribe/"+token+"'>Confirm</a>";
        return htmlBody;
    }


    @Override
    public void sendMailToAllSubscribers(List<MailSuscriber> toList, String subject) {
        for (MailSuscriber mailSuscriber : toList) {
            sendMailTo(mailSuscriber.getEmail(), subject, mailSuscriber.getToken());
        }
    }

}

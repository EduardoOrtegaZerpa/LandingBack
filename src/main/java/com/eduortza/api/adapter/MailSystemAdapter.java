package com.eduortza.api.adapter;

import com.eduortza.api.application.port.out.MailPort;

import com.eduortza.api.domain.MailSuscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Repository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;


@Repository
public class MailSystemAdapter implements MailPort {

    private final JavaMailSender javaMailSender;

    @Value("${app.base.url}")
    private String baseUrl;

    @Autowired
    public MailSystemAdapter(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMail(String from, String subject, String body) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(from);
        mail.setTo("eduardo@eduortza.com");
        mail.setSubject(subject);
        mail.setText(body);

        javaMailSender.send(mail);
    }

    public void sendMailTo(String to, String subject, String token) {
        SimpleMailMessage mail = new SimpleMailMessage();
        String body = generateHtmlBodyWithToken(token);

        mail.setFrom("noreply@eduortza.com");
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(body);

        javaMailSender.send(mail);

    }


    public String generateHtmlBodyWithToken(String token) {
        String unsubscribeUrl = baseUrl + "/unsubscribe/" + token;
        String htmlBody = "<h1>Click the link to unsubscribe</h1><a href='" + unsubscribeUrl + "'>Confirm</a>";
        return htmlBody;
    }


    @Override
    public void sendMailToAllSubscribers(List<MailSuscriber> toList, String subject) {
        for (MailSuscriber mailSuscriber : toList) {
            sendMailTo(mailSuscriber.getEmail(), subject, mailSuscriber.getToken());
        }
    }

}

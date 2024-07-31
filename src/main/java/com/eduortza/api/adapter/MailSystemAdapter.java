package com.eduortza.api.adapter;

import com.eduortza.api.application.port.out.MailPort;
import com.eduortza.api.domain.BlogPost;
import com.eduortza.api.domain.MailSuscriber;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

    public void sendMailTo(String to, String subject, String content) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            messageHelper.setFrom("noreply@eduortza.com");
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending mail");
        }

        try {
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException("Error sending mail");
        }
    }


    public String generateBlogHtmlWithToken(BlogPost blogPost, String token) {
        String unsubscribeUrl = baseUrl + "/unsubscribe/" + token;

        String template;
        try {
            ClassPathResource resource = new ClassPathResource("templates/blog-post.html");
            template = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error loading template");
        } catch (Exception e) {
            throw new RuntimeException("Error loading template");
        }

        return template
                .replace("<!--TITLE-->", blogPost.getTitle())
                .replace("<!--IMAGE_URL-->", blogPost.getImageUrl())
                .replace("<!--DESCRIPTION-->", blogPost.getDescription())
                .replace("<!--CONTENT-->", blogPost.getContent())
                .replace("<!--MINUTES_TO_READ-->", String.valueOf(blogPost.getMinutesToRead()))
                .replace("<!--UNSUBSCRIBE_URL-->", unsubscribeUrl);
    }


    @Override
    public void sendMailToBlogSubscribers(List<MailSuscriber> toList, BlogPost blogPost) {
        for (MailSuscriber mailSuscriber : toList) {
            String htmlBody = generateBlogHtmlWithToken(blogPost, mailSuscriber.getToken());
            String subject = "New blog post: " + blogPost.getTitle();
            sendMailTo(mailSuscriber.getEmail(), subject, htmlBody);
        }
    }

}

package com.eduortza.api.application.port.out.MailSuscriber;

public interface DeleteMailSubscriberPort {
    void delete(String email) throws Exception;
}

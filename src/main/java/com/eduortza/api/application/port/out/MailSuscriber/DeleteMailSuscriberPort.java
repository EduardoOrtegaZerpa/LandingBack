package com.eduortza.api.application.port.out.MailSuscriber;

public interface DeleteMailSuscriberPort {
    void delete(String email) throws Exception;
}

package com.eduortza.api.application.port.out.MailSuscriber;

import com.eduortza.api.application.exception.DeleteException;
import com.eduortza.api.application.exception.MailException;

public interface DeleteMailSubscriberPort {
    void delete(String email);
}

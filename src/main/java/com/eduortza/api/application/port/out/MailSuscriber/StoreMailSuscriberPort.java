package com.eduortza.api.application.port.out.MailSuscriber;

import com.eduortza.api.domain.MailSuscriber;

public interface StoreMailSuscriberPort {
    void store(MailSuscriber mailSuscriber) throws Exception;
}

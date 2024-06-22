package com.eduortza.api.application.service.MailSuscriber;

import com.eduortza.api.application.exception.StoreException;
import com.eduortza.api.application.port.in.MailSuscriber.suscribe.SuscribePort;
import com.eduortza.api.application.port.out.MailSuscriber.StoreMailSuscriberPort;
import com.eduortza.api.common.UseCase;
import com.eduortza.api.domain.MailSuscriber;

@UseCase
public class SuscribeService implements SuscribePort {

    private final StoreMailSuscriberPort storeMailSuscriberPort;

    public SuscribeService(StoreMailSuscriberPort storeMailSuscriberPort) {
        this.storeMailSuscriberPort = storeMailSuscriberPort;
    }

    @Override
    public void suscribe(String email) {
        MailSuscriber mailSuscriber = new MailSuscriber();

        mailSuscriber.setEmail(email);

        try {
            storeMailSuscriberPort.store(mailSuscriber);
        } catch (Exception e) {
            throw new StoreException("Error while trying to store in Database", e);
        }
    }
}

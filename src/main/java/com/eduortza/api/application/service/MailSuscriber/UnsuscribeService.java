package com.eduortza.api.application.service.MailSuscriber;

import com.eduortza.api.application.exception.DeleteException;
import com.eduortza.api.application.port.in.MailSuscriber.unsuscribe.UnsuscribePort;
import com.eduortza.api.application.port.out.MailSuscriber.DeleteMailSuscriberPort;
import com.eduortza.api.common.UseCase;

@UseCase
public class UnsuscribeService implements UnsuscribePort {

    private final DeleteMailSuscriberPort deleteMailSuscriberPort;

    public UnsuscribeService(DeleteMailSuscriberPort deleteMailSuscriberPort) {
        this.deleteMailSuscriberPort = deleteMailSuscriberPort;
    }

    @Override
    public void unsuscribe(String email) {
        try {
            deleteMailSuscriberPort.delete(email);
        } catch (Exception e) {
            throw new DeleteException("Error while trying to delete in Database", e);
        }
    }
}

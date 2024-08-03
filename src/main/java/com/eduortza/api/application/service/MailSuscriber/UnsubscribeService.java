package com.eduortza.api.application.service.MailSuscriber;

import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.application.exception.DeleteException;
import com.eduortza.api.application.port.in.MailSubscriber.unsubscribe.UnsubscribePort;
import com.eduortza.api.application.port.out.MailSuscriber.DeleteMailSubscriberPort;
import com.eduortza.api.common.UseCase;
import jakarta.transaction.Transactional;

@UseCase
public class UnsubscribeService implements UnsubscribePort {

    private final DeleteMailSubscriberPort deleteMailSubscriberPort;

    public UnsubscribeService(DeleteMailSubscriberPort deleteMailSubscriberPort) {
        this.deleteMailSubscriberPort = deleteMailSubscriberPort;
    }

    @Transactional
    @Override
    public void unsubscribe(String email) {
        try {
            deleteMailSubscriberPort.delete(email);
        } catch (NonExistsException e) {
            throw new NonExistsException("MailSubscriber not found", e);
        }
        catch (Exception e) {
            throw new DeleteException("Error while trying to delete in Database", e);
        }
    }
}

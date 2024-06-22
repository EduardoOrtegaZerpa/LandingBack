package com.eduortza.api.adapter;

import com.eduortza.api.adapter.out.persistence.entities.MailSuscriberEntity;
import com.eduortza.api.adapter.out.persistence.mappers.MailSuscriberMapper;
import com.eduortza.api.adapter.out.persistence.repository.SpringMailSuscriberRepository;
import com.eduortza.api.application.port.out.MailSuscriber.DeleteMailSubscriberPort;
import com.eduortza.api.application.port.out.MailSuscriber.GetMailSubscriberPort;
import com.eduortza.api.application.port.out.MailSuscriber.StoreMailSubscriberPort;
import com.eduortza.api.domain.MailSuscriber;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MailSubscriberAdapter implements StoreMailSubscriberPort, DeleteMailSubscriberPort, GetMailSubscriberPort {

    private final SpringMailSuscriberRepository springMailSuscriberRepository;

    public MailSubscriberAdapter(SpringMailSuscriberRepository springMailSuscriberRepository) {
        this.springMailSuscriberRepository = springMailSuscriberRepository;
    }

    @Override
    public void delete(String email) throws Exception {
        springMailSuscriberRepository.deleteByEmail(email);
    }

    @Override
    public void store(MailSuscriber mailSuscriber) throws Exception {
        if (mailSuscriber == null) {
            throw new NullPointerException("MailSubscriber is null");
        }
        springMailSuscriberRepository.save(MailSuscriberMapper.mapToEntity(mailSuscriber));
    }

    @Override
    public List<MailSuscriber> getAllMailSuscriber() {
        List<MailSuscriberEntity> mailSubscriberEntities = springMailSuscriberRepository.findAll();
        return mailSubscriberEntities.stream().map(MailSuscriberMapper::mapToDomain).toList();
    }
}

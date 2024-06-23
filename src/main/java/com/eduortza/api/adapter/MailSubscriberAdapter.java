package com.eduortza.api.adapter;

import com.eduortza.api.adapter.exception.AlreadyExistsException;
import com.eduortza.api.adapter.out.persistence.entities.MailSuscriberEntity;
import com.eduortza.api.adapter.out.persistence.mappers.MailSuscriberMapper;
import com.eduortza.api.adapter.out.persistence.repository.SpringMailSuscriberRepository;
import com.eduortza.api.adapter.out.persistence.services.JwtService;
import com.eduortza.api.application.port.out.MailSuscriber.DeleteMailSubscriberPort;
import com.eduortza.api.application.port.out.MailSuscriber.GetMailSubscriberPort;
import com.eduortza.api.application.port.out.MailSuscriber.StoreMailSubscriberPort;
import com.eduortza.api.domain.MailSuscriber;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MailSubscriberAdapter implements StoreMailSubscriberPort, DeleteMailSubscriberPort, GetMailSubscriberPort {

    private final SpringMailSuscriberRepository springMailSuscriberRepository;
    private final JwtService jwtService;

    public MailSubscriberAdapter(SpringMailSuscriberRepository springMailSuscriberRepository, JwtService jwtService) {
        this.springMailSuscriberRepository = springMailSuscriberRepository;
        this.jwtService = jwtService;
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

        if (mailSuscriber.getEmail() == null) {
            throw new NullPointerException("MailSubscriber email is null");
        }

        if (getMailSuscriber(mailSuscriber.getEmail()) != null) {
            throw new AlreadyExistsException("MailSubscriber already exists");
        }

        String token = jwtService.createJwtTokenEmail(mailSuscriber.getEmail());
        mailSuscriber.setToken(token);

        springMailSuscriberRepository.save(MailSuscriberMapper.mapToEntity(mailSuscriber));
    }

    @Override
    public MailSuscriber getMailSuscriber(String email) {
        MailSuscriberEntity mailSubscriberEntity = springMailSuscriberRepository.findByEmail(email);
        if (mailSubscriberEntity == null) {
            throw new NullPointerException("MailSubscriber not found");
        }
        return MailSuscriberMapper.mapToDomain(mailSubscriberEntity);
    }

    @Override
    public List<MailSuscriber> getAllMailSuscriber() {
        List<MailSuscriberEntity> mailSubscriberEntities = springMailSuscriberRepository.findAll();
        return mailSubscriberEntities.stream().map(MailSuscriberMapper::mapToDomain).toList();
    }
}

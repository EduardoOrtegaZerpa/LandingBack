package com.eduortza.api.adapter;

import com.eduortza.api.adapter.exception.AlreadyExistsException;
import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.adapter.out.persistence.entities.MailSuscriberEntity;
import com.eduortza.api.adapter.out.persistence.mappers.MailSuscriberMapper;
import com.eduortza.api.adapter.out.persistence.repository.SpringMailSuscriberRepository;
import com.eduortza.api.adapter.out.persistence.services.JwtService;
import com.eduortza.api.application.exception.DeleteException;
import com.eduortza.api.application.exception.StoreException;
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
    public void delete(String email) throws DeleteException, NonExistsException {
        if (email == null) {
            throw new NullPointerException("Email is null");
        }

        if (getMailSuscriberEntity(email) == null) {
            throw new NonExistsException("MailSubscriber not found");
        }

        try {
            springMailSuscriberRepository.deleteByEmail(email);
        } catch (Exception e) {
            throw new DeleteException("Error deleting MailSubscriber");
        }
    }

    @Override
    public void store(MailSuscriber mailSuscriber) throws StoreException, AlreadyExistsException {

        if (mailSuscriber == null) {
            throw new NullPointerException("MailSubscriber is null");
        }

        if (mailSuscriber.getEmail() == null) {
            throw new NullPointerException("MailSubscriber email is null");
        }

        if (getMailSuscriberEntity(mailSuscriber.getEmail()) != null) {
            throw new AlreadyExistsException("MailSubscriber already exists");
        }

        String token = jwtService.createJwtTokenEmail(mailSuscriber.getEmail());
        mailSuscriber.setToken(token);

        try {
            springMailSuscriberRepository.save(MailSuscriberMapper.mapToEntity(mailSuscriber));
        } catch (Exception e) {
            throw new StoreException("Error storing MailSubscriber");
        }
    }

    @Override
    public MailSuscriber getMailSuscriber(String email) throws NonExistsException {
        MailSuscriberEntity mailSubscriberEntity = springMailSuscriberRepository.findByEmail(email);
        if (mailSubscriberEntity == null) {
            throw new NonExistsException("MailSubscriber not found");
        }
        return MailSuscriberMapper.mapToDomain(mailSubscriberEntity);
    }

    private MailSuscriberEntity getMailSuscriberEntity(String email) {
        return springMailSuscriberRepository.findByEmail(email);
    }

    @Override
    public List<MailSuscriber> getAllMailSubscriber() {
        List<MailSuscriberEntity> mailSubscriberEntities = springMailSuscriberRepository.findAll();
        return mailSubscriberEntities.stream().map(MailSuscriberMapper::mapToDomain).toList();
    }
}

package com.eduortza.api.adapter;

import com.eduortza.api.adapter.out.persistence.mappers.MailSuscriberMapper;
import com.eduortza.api.adapter.out.persistence.repository.SpringMailSuscriberRepository;
import com.eduortza.api.application.port.out.MailSuscriber.DeleteMailSuscriberPort;
import com.eduortza.api.application.port.out.MailSuscriber.StoreMailSuscriberPort;
import com.eduortza.api.domain.MailSuscriber;
import org.springframework.stereotype.Repository;

@Repository
public class MailSuscriberAdapter implements StoreMailSuscriberPort, DeleteMailSuscriberPort {

    private final SpringMailSuscriberRepository springMailSuscriberRepository;

    public MailSuscriberAdapter(SpringMailSuscriberRepository springMailSuscriberRepository) {
        this.springMailSuscriberRepository = springMailSuscriberRepository;
    }

    @Override
    public void delete(String email) throws Exception {
        springMailSuscriberRepository.deleteByEmail(email);
    }

    @Override
    public void store(MailSuscriber mailSuscriber) throws Exception {
        if (mailSuscriber == null) {
            throw new NullPointerException("MailSuscriber is null");
        }
        springMailSuscriberRepository.save(MailSuscriberMapper.mapToEntity(mailSuscriber));
    }
}

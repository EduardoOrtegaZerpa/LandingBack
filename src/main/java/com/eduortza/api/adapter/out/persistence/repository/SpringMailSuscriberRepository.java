package com.eduortza.api.adapter.out.persistence.repository;

import com.eduortza.api.adapter.out.persistence.entities.MailSuscriberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringMailSuscriberRepository extends JpaRepository<MailSuscriberEntity, Long>{
    void deleteByEmail(String email);
}

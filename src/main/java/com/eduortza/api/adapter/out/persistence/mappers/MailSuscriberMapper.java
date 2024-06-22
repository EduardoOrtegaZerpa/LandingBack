package com.eduortza.api.adapter.out.persistence.mappers;

import com.eduortza.api.domain.MailSuscriber;
import com.eduortza.api.adapter.out.persistence.entities.MailSuscriberEntity;

public class MailSuscriberMapper {

    public static MailSuscriberEntity mapToEntity(MailSuscriber mailSuscriber) {
        return new MailSuscriberEntity(
                mailSuscriber.getId(),
                mailSuscriber.getEmail()

        );
    }

    public static MailSuscriber mapToDomain(MailSuscriberEntity mailSuscriberEntity) {
        return new MailSuscriber(
                mailSuscriberEntity.getId(),
                mailSuscriberEntity.getEmail()
        );
    }
}

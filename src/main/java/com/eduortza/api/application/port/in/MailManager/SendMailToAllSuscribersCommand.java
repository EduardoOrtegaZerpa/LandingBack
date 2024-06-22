package com.eduortza.api.application.port.in.MailManager;

import com.eduortza.api.domain.MailSuscriber;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SendMailToAllSuscribersCommand {
    private List<MailSuscriber> toList;
    private String subject;
    private String text;
}

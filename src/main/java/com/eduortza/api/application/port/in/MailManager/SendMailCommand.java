package com.eduortza.api.application.port.in.MailManager;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendMailCommand {
    private String from;
    private String subject;
    private String text;
    private String name;
}

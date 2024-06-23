package com.eduortza.api.adapter.in.web;

import com.eduortza.api.application.port.in.MailManager.SendMailCommand;
import com.eduortza.api.application.port.in.MailManager.SendMailPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    private final SendMailPort sendMailPort;

    public MailController(SendMailPort sendMailPort) {
        this.sendMailPort = sendMailPort;
    }

    @PostMapping("/sendMail")
    public ResponseEntity<Object> sendMail(@RequestBody SendMailCommand command) {
        try {
            sendMailPort.sendMail(command);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

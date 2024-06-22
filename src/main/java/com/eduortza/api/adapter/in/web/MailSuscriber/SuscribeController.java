package com.eduortza.api.adapter.in.web.MailSuscriber;

import com.eduortza.api.adapter.exception.SuscribeException;
import com.eduortza.api.application.port.in.MailSuscriber.suscribe.SuscribePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuscribeController {

    private final SuscribePort suscribePort;

    public SuscribeController(SuscribePort suscribePort) {
        this.suscribePort = suscribePort;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<Object> subscribe(@RequestBody String email) {
        try {
            suscribePort.suscribe(email);
            return ResponseEntity.status(HttpStatus.CREATED).body("Suscribed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SuscribeException(e.getMessage()));
        }
    }
}

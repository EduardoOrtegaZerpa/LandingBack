package com.eduortza.api.adapter.in.web.MailSuscriber;

import com.eduortza.api.adapter.exception.AlreadyExistsException;
import com.eduortza.api.adapter.exception.SuscribeException;
import com.eduortza.api.application.port.in.MailSubscriber.subscribe.SubscribePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscribeController {

    private final SubscribePort subscribePort;

    public SubscribeController(SubscribePort subscribePort) {
        this.subscribePort = subscribePort;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<Object> subscribe(@RequestBody String email) {
        try {
            subscribePort.subscribe(email);
            return ResponseEntity.status(HttpStatus.CREATED).body("Subscribed");
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new AlreadyExistsException(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SuscribeException(e.getMessage()));
        }
    }
}

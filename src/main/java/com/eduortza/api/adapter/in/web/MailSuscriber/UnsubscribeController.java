package com.eduortza.api.adapter.in.web.MailSuscriber;

import com.eduortza.api.adapter.exception.JwtAuthorizationException;
import com.eduortza.api.adapter.exception.UnsuscribeException;
import com.eduortza.api.adapter.out.persistence.services.JwtService;
import com.eduortza.api.application.port.in.MailSubscriber.unsubscribe.UnsubscribePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnsubscribeController {

     private final UnsubscribePort unsubscribePort;
     private final JwtService jwtService;

     public UnsubscribeController(UnsubscribePort unsubscribePort, JwtService jwtService) {
         this.unsubscribePort = unsubscribePort;
            this.jwtService = jwtService;
     }

     @DeleteMapping("/unsubscribe/{token}")
     public ResponseEntity<Object> unsubscribe(@PathVariable String token) {
         try {
             String email = jwtService.getEmailFromToken(token);
             unsubscribePort.unsubscribe(email);
             return ResponseEntity.status(HttpStatus.CREATED).body("Unsubscribed");
         } catch (JwtAuthorizationException e) {
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new UnsuscribeException(e.getMessage()));
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UnsuscribeException(e.getMessage()));
         }
     }
}

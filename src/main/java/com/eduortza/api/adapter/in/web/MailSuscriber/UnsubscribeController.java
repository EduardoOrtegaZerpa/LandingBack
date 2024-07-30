package com.eduortza.api.adapter.in.web.MailSuscriber;

import com.eduortza.api.adapter.exception.JwtAuthorizationException;
import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.adapter.exception.UnsuscribeException;
import com.eduortza.api.adapter.out.persistence.services.JwtService;
import com.eduortza.api.application.port.in.MailSubscriber.unsubscribe.UnsubscribePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class UnsubscribeController {

     private final UnsubscribePort unsubscribePort;
     private final JwtService jwtService;

    @Value("${frontend.url}")
    private String frontendUrl;

     public UnsubscribeController(UnsubscribePort unsubscribePort, JwtService jwtService) {
         this.unsubscribePort = unsubscribePort;
            this.jwtService = jwtService;
     }

    @GetMapping("/unsubscribe/{token}")
    public ResponseEntity<Object> unsubscribe(@PathVariable String token) {
        try {
            String email = jwtService.getEmailFromToken(token);
            unsubscribePort.unsubscribe(email);
            return buildRedirectResponse(frontendUrl + "/unsubscribe");
        } catch (Exception e) {
            return buildRedirectResponse(frontendUrl + "/notAvailable");
        }
    }

    private ResponseEntity<Object> buildRedirectResponse(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}

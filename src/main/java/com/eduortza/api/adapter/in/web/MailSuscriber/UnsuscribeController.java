package com.eduortza.api.adapter.in.web.MailSuscriber;

import com.eduortza.api.adapter.exception.UnsuscribeException;
import com.eduortza.api.application.port.in.MailSuscriber.unsuscribe.UnsuscribePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnsuscribeController {

     private final UnsuscribePort unsuscribePort;

     public UnsuscribeController(UnsuscribePort unsuscribePort) {
         this.unsuscribePort = unsuscribePort;
     }

     @DeleteMapping("/unsubscribe")
     public ResponseEntity<Object> unsubscribe(@RequestBody String email) {
         try {
             unsuscribePort.unsuscribe(email);
             return ResponseEntity.status(HttpStatus.CREATED).body("Unsuscribed");
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UnsuscribeException(e.getMessage()));
         }
     }
}

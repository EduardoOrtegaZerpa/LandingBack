package com.eduortza.api.adapter.in.web.User;

import com.eduortza.api.adapter.exception.JwtAuthorizationException;
import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.adapter.exception.UserException;
import com.eduortza.api.adapter.out.persistence.services.JwtService;
import com.eduortza.api.application.port.in.User.modify.ModifyUserCommand;
import com.eduortza.api.application.port.in.User.modify.ModifyUserPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.desktop.UserSessionEvent;

@RestController
public class ModifyUserController {

    private final ModifyUserPort modifyUserPort;
    private final JwtService jwtService;

    public ModifyUserController(ModifyUserPort modifyUserPort, JwtService jwtService) {
        this.modifyUserPort = modifyUserPort;
        this.jwtService = jwtService;
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Object> modifyUser(@PathVariable long id, @RequestBody ModifyUserCommand modifyUserCommand) {
        try {
            jwtService.authorizeAdminAccess();
            modifyUserPort.modifyUser(id, modifyUserCommand);
            return ResponseEntity.ok().build();
        } catch (NonExistsException e) {
            return ResponseEntity.status(404).body(new UserException("User with id " + id + " does not exist"));
        } catch (JwtAuthorizationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new UserException(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new UserException("Error modifying user", e));
        }
    }
}

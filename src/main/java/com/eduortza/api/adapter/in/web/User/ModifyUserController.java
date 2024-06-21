package com.eduortza.api.adapter.in.web.User;

import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.adapter.exception.UserException;
import com.eduortza.api.application.port.in.User.modify.ModifyUserCommand;
import com.eduortza.api.application.port.in.User.modify.ModifyUserPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.desktop.UserSessionEvent;

@RestController
public class ModifyUserController {

    private final ModifyUserPort modifyUserPort;

    public ModifyUserController(ModifyUserPort modifyUserPort) {
        this.modifyUserPort = modifyUserPort;
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Object> modifyUser(@PathVariable long id, @RequestBody ModifyUserCommand modifyUserCommand) {
        try {
            modifyUserPort.modifyUser(id, modifyUserCommand);
            return ResponseEntity.ok().build();
        } catch (NonExistsException e) {
            return ResponseEntity.status(404).body(new UserException("User with id " + id + " does not exist"));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(new UserException("Error modifying user", e));
        }
    }
}

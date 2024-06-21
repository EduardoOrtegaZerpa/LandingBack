package com.eduortza.api.adapter.in.web.User;

import com.eduortza.api.application.port.in.User.modify.ModifyUserCommand;
import com.eduortza.api.application.port.in.User.modify.ModifyUserPort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModifyUserController {

    private final ModifyUserPort modifyUserPort;

    public ModifyUserController(ModifyUserPort modifyUserPort) {
        this.modifyUserPort = modifyUserPort;
    }

    @PutMapping("/user/{id}")
    public void modifyUser(@PathVariable long id, @RequestBody ModifyUserCommand modifyUserCommand) {
        modifyUserPort.modifyUser(id, modifyUserCommand);
    }
}

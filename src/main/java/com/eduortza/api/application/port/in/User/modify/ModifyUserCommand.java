package com.eduortza.api.application.port.in.User.modify;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModifyUserCommand {
    private String username;
    private String password;
}

package com.eduortza.api.application.port.in.User.load;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoadUserCommand {
    private String username;
    private String password;
}

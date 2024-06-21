package com.eduortza.api.application.port.in.User.load;

import com.eduortza.api.domain.User;

public interface LoadUserPort {
    User loadUser(LoadUserCommand command);
}

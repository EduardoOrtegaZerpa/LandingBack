package com.eduortza.api.application.port.out.User;

import com.eduortza.api.domain.User;

public interface GetUserPort {
    User get(String username);
    User get(long id);
}

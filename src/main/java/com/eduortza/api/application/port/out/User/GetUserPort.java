package com.eduortza.api.application.port.out.User;

import com.eduortza.api.domain.User;

public interface GetUserPort {
    User get(String username) throws Exception;
    User get(long id) throws Exception;
}

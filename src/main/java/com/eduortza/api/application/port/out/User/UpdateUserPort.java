package com.eduortza.api.application.port.out.User;

import com.eduortza.api.domain.User;

public interface UpdateUserPort {
    void update(User user) throws Exception;
}

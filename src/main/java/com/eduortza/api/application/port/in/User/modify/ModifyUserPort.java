package com.eduortza.api.application.port.in.User.modify;

public interface ModifyUserPort {
    void modifyUser(long id, ModifyUserCommand command);
}

package com.eduortza.api.application.service.User;

import com.eduortza.api.application.exception.LoadingException;
import com.eduortza.api.application.exception.StoreException;
import com.eduortza.api.application.port.in.User.modify.ModifyUserCommand;
import com.eduortza.api.application.port.in.User.modify.ModifyUserPort;
import com.eduortza.api.application.port.out.User.GetUserPort;
import com.eduortza.api.application.port.out.User.UpdateUserPort;
import com.eduortza.api.common.UseCase;
import com.eduortza.api.domain.User;
import jakarta.transaction.Transactional;

@UseCase
public class ModifyUserService implements ModifyUserPort {

    private final UpdateUserPort updateUserPort;
    private final GetUserPort getUserPort;

    public ModifyUserService(UpdateUserPort updateUserPort, GetUserPort getUserPort) {
        this.updateUserPort = updateUserPort;
        this.getUserPort = getUserPort;
    }

    @Transactional
    @Override
    public void modifyUser(long id, ModifyUserCommand modifyUserCommand) {
        User user;

        try {
            user = getUserPort.get(id);
        } catch (Exception e) {
            throw new LoadingException("Error while trying to load user from Database", e);
        }

        if (user == null) {
            throw new NullPointerException("User is null");
        }

        if (modifyUserCommand.getUsername() != null) {
            user.setUsername(modifyUserCommand.getUsername());
        }


        if (modifyUserCommand.getPassword() != null) {
            user.setPassword(modifyUserCommand.getPassword());
        }


        try {
            updateUserPort.update(user);
        } catch (Exception e) {
            throw new StoreException("Error while trying to update user in Database", e);
        }
    }

}

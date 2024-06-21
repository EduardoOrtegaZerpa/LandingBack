package com.eduortza.api.application.service.User;

import com.eduortza.api.application.exception.LoadingException;
import com.eduortza.api.application.port.in.User.load.LoadUserCommand;
import com.eduortza.api.application.port.in.User.load.LoadUserPort;
import com.eduortza.api.application.port.out.User.GetUserPort;
import com.eduortza.api.common.UseCase;
import com.eduortza.api.domain.User;
import jakarta.transaction.Transactional;

@UseCase
public class LoadUserService implements LoadUserPort {

    private final GetUserPort getUserPort;

    public LoadUserService(GetUserPort getUserPort) {
        this.getUserPort = getUserPort;
    }

    @Transactional
    @Override
    public User loadUser(LoadUserCommand command) {
        try {
            String username = command.getUsername();
            return getUserPort.get(username);
        } catch (Exception e) {
            throw new LoadingException("Error while trying to load from Database", e);
        }
    }
}

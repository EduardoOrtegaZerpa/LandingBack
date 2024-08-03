package com.eduortza.api.adapter;

import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.adapter.out.persistence.mappers.UserMapper;
import com.eduortza.api.adapter.out.persistence.repository.SpringUserRepository;

import com.eduortza.api.application.exception.StoreException;
import com.eduortza.api.application.port.out.User.GetUserPort;
import com.eduortza.api.application.port.out.User.UpdateUserPort;
import com.eduortza.api.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserAdapter implements GetUserPort, UpdateUserPort {

    private final SpringUserRepository springUserRepository;

    public UserAdapter(SpringUserRepository springUserRepository) {
        this.springUserRepository = springUserRepository;
    }

    @Override
    public void update(User user) throws NonExistsException, StoreException {
        if (user == null) {
            throw new NullPointerException("User is null");
        }

        if (!springUserRepository.existsById(user.getId())) {
            throw new NonExistsException("User with id " + user.getId() + " does not exist");
        }

        try {
            springUserRepository.save(UserMapper.mapToEntity(user));
        } catch (Exception e) {
            throw new StoreException("Error updating User entity.");
        }
    }


    @Override
    public User get(String username) throws NonExistsException {
        if (username == null) {
            throw new NullPointerException("Username or password is null");
        }

        return UserMapper.mapToDomain(springUserRepository.findByUsername(username).orElseThrow( () -> new NonExistsException("User with username " + username + " does not exist")));
    }

    @Override
    public User get(long id) throws NonExistsException {
        if (!springUserRepository.existsById(id)) {
            throw new NonExistsException("User with id " + id + " does not exist");
        }

        return UserMapper.mapToDomain(springUserRepository.findById(id).orElseThrow( () -> new NonExistsException("User with id " + id + " does not exist")));
    }
}

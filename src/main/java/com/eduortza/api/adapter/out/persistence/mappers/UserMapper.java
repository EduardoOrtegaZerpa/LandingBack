package com.eduortza.api.adapter.out.persistence.mappers;

import com.eduortza.api.adapter.out.persistence.entities.UserEntity;
import com.eduortza.api.domain.User;

public class UserMapper {

    public static UserEntity mapToEntity(User user) {
        return new UserEntity(
                user.getId(),
                user.getUsername(),
                user.getPassword()
        );
    }

    public static User mapToDomain(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getPassword()
        );
    }
}

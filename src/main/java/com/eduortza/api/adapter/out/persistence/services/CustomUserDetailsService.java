package com.eduortza.api.adapter.out.persistence.services;

import com.eduortza.api.adapter.out.persistence.entities.UserEntity;
import com.eduortza.api.adapter.out.persistence.repository.SpringUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final SpringUserRepository springUserRepository;

    public CustomUserDetailsService(SpringUserRepository springUserRepository) {
        this.springUserRepository = springUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = springUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        if(userEntity != null) {
            var springUser = User.withUsername(userEntity.getUsername())
                    .password(userEntity.getPassword())
                    .roles("ADMIN")
                    .build();

            return springUser;
        }

        return null;
    }
}

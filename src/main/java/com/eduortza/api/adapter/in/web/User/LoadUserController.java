package com.eduortza.api.adapter.in.web.User;

import com.eduortza.api.adapter.out.persistence.entities.UserEntity;
import com.eduortza.api.adapter.out.persistence.mappers.UserMapper;
import com.eduortza.api.adapter.out.persistence.services.JwtService;
import com.eduortza.api.application.port.in.User.load.LoadUserCommand;
import com.eduortza.api.application.port.in.User.load.LoadUserPort;
import com.eduortza.api.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class LoadUserController {

    private final LoadUserPort loadUserPort;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private static final Logger logger = LoggerFactory.getLogger(LoadUserController.class);


    public LoadUserController(LoadUserPort loadUserPort, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.loadUserPort = loadUserPort;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoadUserCommand command) {
        try {
            logger.info("Login request received for user: {}", command.getUsername());

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            command.getUsername(),
                            command.getPassword()
                    )
            );

            logger.info("Authentication successful");

            UserEntity userEntity = UserMapper.mapToEntity(loadUserPort.loadUser(command));
            logger.info("User loaded from database: {}", userEntity.getUsername());

            String jwt = this.jwtService.createJwtToken(userEntity);
            logger.info("JWT token created");

            User user = UserMapper.mapToDomain(userEntity);

            var response = new HashMap<String, Object>();
            response.put("token", jwt);
            response.put("user", user);

            logger.info("Login successful");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            String errorMessage = (e.getMessage() != null) ? e.getMessage() : "An error occurred";
            logger.info("Login failed: {}", errorMessage);
            logger.warn("Login failed: {}", errorMessage);
            return e.getMessage() != null ? ResponseEntity.badRequest().body("error") : ResponseEntity.badRequest().build();
        }
    }

}
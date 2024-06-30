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
import java.util.Map;

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
        var response = new HashMap<String, Object>();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            command.getUsername(),
                            command.getPassword()
                    )
            );

            UserEntity userEntity = UserMapper.mapToEntity(loadUserPort.loadUser(command));

            String jwt = this.jwtService.createJwtToken(userEntity);
            User user = UserMapper.mapToDomain(userEntity);

            response.put("token", jwt);
            response.put("user", user);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/validate/token")
    public ResponseEntity<Object> validateToken(@RequestHeader("Authorization") String token) {
        var response = new HashMap<String, Object>();
        try {
            String jwt = token.substring(7);
            boolean isValid = jwtService.validateToken(jwt);
            response.put("isValid", isValid);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

}
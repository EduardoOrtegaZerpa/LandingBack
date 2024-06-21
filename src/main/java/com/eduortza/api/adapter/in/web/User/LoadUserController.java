package com.eduortza.api.adapter.in.web.User;

import com.eduortza.api.adapter.out.persistence.entities.UserEntity;
import com.eduortza.api.adapter.out.persistence.mappers.UserMapper;
import com.eduortza.api.application.port.in.User.load.LoadUserCommand;
import com.eduortza.api.application.port.in.User.load.LoadUserPort;
import com.eduortza.api.domain.User;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;

@RestController
public class LoadUserController {

    private final LoadUserPort loadUserPort;
    private final AuthenticationManager authenticationManager;


    @Value("${security.jwt.secret-key}")
    private String JWT_SECRET_KEY;

    @Value("${security.jwt.issuer}")
    private String JWT_ISSUER;

    public LoadUserController(LoadUserPort loadUserPort, AuthenticationManager authenticationManager) {
        this.loadUserPort = loadUserPort;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoadUserCommand command) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            command.getUsername(),
                            command.getPassword()
                    )
            );

            UserEntity userEntity = UserMapper.mapToEntity(loadUserPort.loadUser(command));

            String jwt = createJwtToken(userEntity);
            User user = UserMapper.mapToDomain(userEntity);

            var response = new HashMap<String, Object>();
            response.put("token", jwt);
            response.put("user", user);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    private String createJwtToken(UserEntity userEntity) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(JWT_ISSUER)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(24*3600))
                .subject(userEntity.getUsername())
                .build();

        var encoder = new NimbusJwtEncoder(
                new ImmutableSecret<>(JWT_SECRET_KEY.getBytes())
        );

        var params = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(), claims
        );

        return encoder.encode(params).getTokenValue();
    }
}

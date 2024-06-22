package com.eduortza.api.adapter.out.persistence.services;

import com.eduortza.api.adapter.exception.JwtAuthenticationException;
import com.eduortza.api.adapter.exception.JwtAuthorizationException;
import com.eduortza.api.adapter.out.persistence.entities.UserEntity;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;

@Service
public class JwtService {

    private final JwtDecoder jwtDecoder;

    public JwtService(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Value("${security.jwt.secret-key}")
    private String JWT_SECRET_KEY;

    @Value("${security.jwt.issuer}")
    private String JWT_ISSUER;

    public Jwt extractJwtFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof JwtAuthenticationToken)) {
            throw new JwtAuthenticationException("No JWT found in the current SecurityContext");
        }
        return ((JwtAuthenticationToken) authentication).getToken();
    }

    public String getUsernameFromJwt(Jwt jwt) {
        return jwt.getSubject();
    }

    public String getRoleFromJwt(Jwt jwt) {
        return jwt.getClaimAsString("role");
    }

    public void validateAdminRoleOrThrow(Jwt jwt) {
        String role = getRoleFromJwt(jwt);
        if (!"ADMIN".equals(role)) {
            throw new JwtAuthorizationException("User does not have the necessary permissions (ADMIN)");
        }
    }

    public void authorizeAdminAccess() {
        try {
            Jwt jwt = extractJwtFromAuthentication();
            validateAdminRoleOrThrow(jwt);
        } catch (JwtAuthenticationException | JwtAuthorizationException e) {
            throw new JwtAuthorizationException("Unauthorized");
        }
    }

    public String getEmailFromToken(String token) {
        try {
            Jwt jwt = jwtDecoder.decode(token);
            return jwt.getSubject();
        } catch (JwtAuthenticationException | JwtAuthorizationException e) {
            throw new JwtAuthorizationException("Unauthorized");
        }
    }

    public String createJwtToken(UserEntity userEntity) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(JWT_ISSUER)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(24*3600))
                .subject(userEntity.getUsername())
                .claim("role", "ADMIN")
                .build();

        var encoder = new NimbusJwtEncoder(
                new ImmutableSecret<>(JWT_SECRET_KEY.getBytes())
        );

        var params = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(), claims
        );

        return encoder.encode(params).getTokenValue();
    }


    public String createJwtTokenEmail(String email) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(JWT_ISSUER)
                .issuedAt(now)
                .subject(email)
                .claim("role", "USER")
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

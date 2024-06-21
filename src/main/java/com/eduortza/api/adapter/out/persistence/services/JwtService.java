package com.eduortza.api.adapter.out.persistence.services;

import com.eduortza.api.adapter.exception.JwtAuthenticationException;
import com.eduortza.api.adapter.exception.JwtAuthorizationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

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

    public boolean authorizeAdminAccess() {
        try {
            Jwt jwt = extractJwtFromAuthentication();
            validateAdminRoleOrThrow(jwt);
            return true;
        } catch (JwtAuthenticationException | JwtAuthorizationException e) {
            throw new JwtAuthorizationException("Unauthorized");
        }

    }
}

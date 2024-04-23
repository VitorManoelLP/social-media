package com.socialmedia.main.util;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithKeycloakMockUserFactory implements WithSecurityContextFactory<WithKeycloakMockUser> {

    public static final String FAKE_JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6ImMzZGZhYWFlLWU4NzEtNDNjOS05ZDE3LTA1NmQyM2U1ZThjMiIsImlhdCI6MTUxNjIzOTAyMn0.h_xnWR7oOIivSL6Nv0CZrJEaSVxvMERRvIav5baCqBo";

    @Override
    public SecurityContext createSecurityContext(WithKeycloakMockUser annotation) {
        return createKeycloakSecurity(annotation);
    }

    private SecurityContext createKeycloakSecurity(WithKeycloakMockUser mockUser) {
        final SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();

        final JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(
                Jwt.withTokenValue(FAKE_JWT)
                        .header("alg", "HS256")
                        .claim("sub", mockUser.principal())
                        .build(),
                List.of(new SimpleGrantedAuthority("ADMIN"))
        );

        emptyContext.setAuthentication(jwtAuthenticationToken);

        return emptyContext;
    }

}

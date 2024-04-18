package com.socialmedia.main.infra.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfiguration {

    @Bean
    @SuppressWarnings("unchecked")
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        final Converter<Jwt, Collection<GrantedAuthority>> jwtCollectionConverter = jwt -> {
            final Map<String, Object> resourceAccess = jwt.getClaim("realm_access");
            Collection<String> roles = (Collection<String>) resourceAccess.get("roles");
            return roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        };
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtCollectionConverter);
        return jwtAuthenticationConverter;
    }

}
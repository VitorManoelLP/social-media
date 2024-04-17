package com.auth.service.infra.property;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.keycloak")
public class KeycloakProperties {

    private String clientId;
    private String clientSecret;
    private String scope;
    private String authUri;
    private String realm;
    private KeycloakEndpoints keycloakEndpoints;

    public String getTokenEndpoint() {
        return String.format("%s%s", authUri, keycloakEndpoints.token());
    }

    public String getValidateTokenEndpoint() {
        return String.format("%s%s", authUri, keycloakEndpoints.validateToken());
    }

    private record KeycloakEndpoints(String token, String validateToken) {
    }

    public KeycloakProperties() {
        keycloakEndpoints = new KeycloakEndpoints("/protocol/openid-connect/token", "/protocol/openid-connect/certs");
    }

}

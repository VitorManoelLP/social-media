package com.auth.service.infra.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.keycloak")
public class KeycloakProperties {

    private String clientId;
    private String clientSecret;
    private String realm;
    private String scope;
    private String authUri;
    private KeycloakEndpoints keycloakEndpoints;

    public String getTokenEndpoint() {
        return getTokenEndpoint(realm);
    }

    public String getValidateTokenEndpoint() {
        return getValidateTokenEndpoint(realm);
    }

    public String getTokenEndpoint(String realm) {
        return String.format("%s%s", getRealmUri(realm), keycloakEndpoints.token());
    }

    public String getValidateTokenEndpoint(String realm) {
        return String.format("%s%s", getRealmUri(realm), keycloakEndpoints.validateToken());
    }

    public String getCreateUserEndpoint() {
        return String.format("%s%s", getAdminUri(), "/users");
    }

    private String getAdminUri() {
        return authUri + String.format(keycloakEndpoints.adminRealm(), realm);
    }

    public String getRealmUri(String realm) {
        return authUri + String.format(keycloakEndpoints.realmUri(), realm);
    }


    private record KeycloakEndpoints(String adminRealm, String realmUri, String token, String validateToken) {
    }

    public KeycloakProperties() {
        keycloakEndpoints = new KeycloakEndpoints(
                "/admin/realms/%s",
                "/realms/%s",
                "/protocol/openid-connect/token",
                "/protocol/openid-connect/certs"
        );
    }

}

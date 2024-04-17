package com.auth.service.fixture;

import com.auth.service.infra.property.KeycloakProperties;

public final class KeycloakPropertiesFixture {

    private KeycloakPropertiesFixture() {}

    public static KeycloakProperties createKeycloakProperties() {
        final KeycloakProperties keycloakProperties = new KeycloakProperties();
        keycloakProperties.setScope("scope");
        keycloakProperties.setRealm("realm");
        keycloakProperties.setAuthUri("localhost:8080");
        keycloakProperties.setClientSecret("secret");
        keycloakProperties.setClientId("client_id");
        return keycloakProperties;
    }

}

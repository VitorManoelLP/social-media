package com.auth.service.infra.context;

import com.auth.service.domain.KeycloakClient;
import jakarta.inject.Singleton;
import lombok.EqualsAndHashCode;
import org.springframework.core.NamedInheritableThreadLocal;

import java.util.Optional;

@Singleton
@EqualsAndHashCode
public final class ClientContextHolder {

    private static final ClientContextHolder clientContextHolder = new ClientContextHolder();

    private static final ThreadLocal<KeycloakClient> KEYCLOAK_CLIENT = new NamedInheritableThreadLocal<>("CLIENT");

    public KeycloakClient getClient() {
        return KEYCLOAK_CLIENT.get();
    }

    public boolean missingClient() {
        return Optional.ofNullable(getClient()).isEmpty();
    }

    public void reset() {
        KEYCLOAK_CLIENT.remove();
    }

    public void setClient(KeycloakClient keycloakClient) {
        KEYCLOAK_CLIENT.set(keycloakClient);
    }

    private ClientContextHolder() {
    }

    public static ClientContextHolder getInstance() {
        return clientContextHolder;
    }

}

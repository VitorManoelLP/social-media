package com.auth.service.fixture;

import com.auth.service.domain.KeycloakClient;
import com.auth.service.infra.context.ClientContextHolder;

public final class ClientContextHolderFixture {

    private ClientContextHolderFixture() {}

    public static void mockContextHolder() {
        ClientContextHolder.getInstance().setClient(new KeycloakClient(
                "token",
                KeycloakPropertiesFixture.createKeycloakProperties()
        ));
    }

    public static void clearContextHolder() {
        ClientContextHolder.getInstance().reset();
    }

}

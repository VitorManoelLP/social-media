package com.auth.service.fixture;

import com.auth.service.domain.KeycloakClient;
import com.auth.service.infra.context.ClientContextHolder;
import com.auth.service.infra.util.JwtToken;

public final class ClientContextHolderFixture {

    private ClientContextHolderFixture() {}

    public static void mockContextHolder() {
        ClientContextHolder.getInstance().setClient(new KeycloakClient(
                new JwtToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"),
                KeycloakPropertiesFixture.createKeycloakProperties()
        ));
    }

    public static void clearContextHolder() {
        ClientContextHolder.getInstance().reset();
    }

}

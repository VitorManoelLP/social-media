package com.auth.service.domain;

import com.auth.service.infra.property.KeycloakProperties;
import com.auth.service.infra.util.JwtToken;
import jakarta.validation.constraints.NotNull;

public record KeycloakClient(@NotNull JwtToken jwt, @NotNull KeycloakProperties keycloakProperties) {
}

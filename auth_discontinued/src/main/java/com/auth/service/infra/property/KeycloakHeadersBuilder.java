package com.auth.service.infra.property;

import com.auth.service.domain.UserRepresentation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public final class KeycloakHeadersBuilder {

    private KeycloakHeadersBuilder() {
    }

    private final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    private UserRepresentation body;

    public static KeycloakHeadersBuilder builder(KeycloakProperties properties) {
        final KeycloakHeadersBuilder httpParamBuilder = new KeycloakHeadersBuilder();
        httpParamBuilder.params.add("client_id", properties.getClientId());
        httpParamBuilder.params.add("client_secret", properties.getClientSecret());
        return httpParamBuilder;
    }

    public static KeycloakHeadersBuilder builder(UserRepresentation body) {
        final KeycloakHeadersBuilder httpParamBuilder = new KeycloakHeadersBuilder();
        httpParamBuilder.body = body;
        return httpParamBuilder;
    }

    public static KeycloakHeadersBuilder builder(String clientId, String clientSecret) {
        final KeycloakHeadersBuilder httpParamBuilder = new KeycloakHeadersBuilder();
        httpParamBuilder.params.add("client_id", clientId);
        httpParamBuilder.params.add("client_secret", clientSecret);
        return httpParamBuilder;
    }

    public KeycloakHeadersBuilder withGrantType(String grantType) {
        params.add("grant_type", grantType);
        return this;
    }

    public KeycloakHeadersBuilder withUsername(String username) {
        params.add("username", username);
        return this;
    }

    public KeycloakHeadersBuilder withPassword(String password) {
        params.add("password", password);
        return this;
    }

    public KeycloakHeadersBuilder withRefreshToken(String refreshToken) {
        params.add("refresh_token", refreshToken);
        return this;
    }

    public HttpEntity<UserRepresentation> build(String token) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(token);
        return new HttpEntity<>(body, httpHeaders);
    }

    public HttpEntity<MultiValueMap<String, String>> build() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return new HttpEntity<>(params, httpHeaders);
    }

}

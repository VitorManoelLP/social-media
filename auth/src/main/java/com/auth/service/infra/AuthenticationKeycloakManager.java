package com.auth.service.infra;

import com.auth.service.application.AuthenticationManager;
import com.auth.service.domain.*;
import com.auth.service.infra.context.ClientContextHolder;
import com.auth.service.infra.property.KeycloakHeadersBuilder;
import com.auth.service.infra.property.KeycloakProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AuthenticationKeycloakManager implements AuthenticationManager {

    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<?> login(UserLogin user) {

        final KeycloakClient client = ClientContextHolder.getInstance().getClient();
        final KeycloakProperties keycloakProperties = client.keycloakProperties();

        final HttpEntity<MultiValueMap<String, String>> keycloakEntity = KeycloakHeadersBuilder.builder(keycloakProperties)
                .withUsername(user.username())
                .withPassword(user.password())
                .withGrantType("password")
                .build();

        try {

            return restTemplate.postForEntity(
                    keycloakProperties.getTokenEndpoint(),
                    keycloakEntity,
                    String.class
            );

        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }

    }

    @Override
    public ResponseEntity<?> refresh(String refreshToken) {

        final KeycloakClient client = ClientContextHolder.getInstance().getClient();
        final KeycloakProperties keycloakProperties = client.keycloakProperties();

        final HttpEntity<MultiValueMap<String, String>> keycloakEntity = KeycloakHeadersBuilder.builder(keycloakProperties)
                .withGrantType("refresh_token")
                .withRefreshToken(refreshToken)
                .build();

        try {

            return restTemplate.postForEntity(
                    keycloakProperties.getTokenEndpoint(),
                    keycloakEntity,
                    String.class
            );

        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> create(UserSignUp userSignUp) {

        final KeycloakClient client = ClientContextHolder.getInstance().getClient();
        final KeycloakProperties keycloakProperties = client.keycloakProperties();

        final HttpEntity<UserRepresentation> keycloakEntity = KeycloakHeadersBuilder.builder(UserRepresentation.of(userSignUp))
                .build(client.jwt().getToken());

        try {

            return restTemplate.postForEntity(
                    keycloakProperties.getCreateUserEndpoint(),
                    keycloakEntity,
                    Void.class
            );

        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }

    }

}

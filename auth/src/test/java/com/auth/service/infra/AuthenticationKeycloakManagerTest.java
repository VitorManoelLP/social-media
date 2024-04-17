package com.auth.service.infra;

import com.auth.service.domain.UserLogin;
import com.auth.service.fixture.KeycloakPropertiesFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationKeycloakManagerTest {

    private AuthenticationKeycloakManager authenticationKeycloakManager;

    @Mock
    private RestTemplate restTemplate;

    @Captor
    private ArgumentCaptor<HttpEntity<MultiValueMap<String, String>>> captor;

    @BeforeEach
    public void setup() {
        authenticationKeycloakManager = new AuthenticationKeycloakManager(KeycloakPropertiesFixture.createKeycloakProperties(), restTemplate);
    }

    @Test
    public void shouldLoginWithRightHeaders() {

        when(restTemplate.postForEntity(eq("localhost:8080/protocol/openid-connect/token"), any(), eq(String.class))).thenReturn(ResponseEntity.ok("token"));

        final ResponseEntity<?> response = authenticationKeycloakManager.login(new UserLogin("username", "password"));

        verify(restTemplate, atLeastOnce()).postForEntity(eq("localhost:8080/protocol/openid-connect/token"), captor.capture(), eq(String.class));

        final HttpEntity<MultiValueMap<String, String>> entity = captor.getValue();

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo("token");

        Assertions.assertThat(entity.getHeaders())
                .extracting(header -> header.get(HttpHeaders.CONTENT_TYPE))
                .isEqualTo(List.of(MediaType.APPLICATION_FORM_URLENCODED.toString()));

        Assertions.assertThat(entity.getBody())
                .isNotEmpty()
                .containsOnlyKeys("client_id", "client_secret", "username", "password", "grant_type")
                .containsValues(List.of("client_id"), List.of("secret"), List.of("username"), List.of("password"), List.of("password"));
    }

    @Test
    public void shouldReturnResponseWithExceptionWhenRestTemplateDoThrow() {
        when(restTemplate.postForEntity(eq("localhost:8080/protocol/openid-connect/token"), any(), eq(String.class))).thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN, "Forbidden"));
        final ResponseEntity<?> response = authenticationKeycloakManager.login(new UserLogin("username", "password"));
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        Assertions.assertThat(response.getBody()).isEqualTo("403 Forbidden");
    }

    @Test
    public void shouldRefreshTokenWithRightHeaders() {

        when(restTemplate.postForEntity(eq("localhost:8080/protocol/openid-connect/token"), any(), eq(String.class))).thenReturn(ResponseEntity.ok("token"));

        final ResponseEntity<?> response = authenticationKeycloakManager.refresh("token");

        verify(restTemplate, atLeastOnce()).postForEntity(eq("localhost:8080/protocol/openid-connect/token"), captor.capture(), eq(String.class));

        final HttpEntity<MultiValueMap<String, String>> entity = captor.getValue();

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo("token");

        Assertions.assertThat(entity.getHeaders())
                .extracting(header -> header.get(HttpHeaders.CONTENT_TYPE))
                .isEqualTo(List.of(MediaType.APPLICATION_FORM_URLENCODED.toString()));

        Assertions.assertThat(entity.getBody())
                .isNotEmpty()
                .containsOnlyKeys("client_id", "client_secret", "grant_type", "refresh_token")
                .containsValues(List.of("client_id"), List.of("secret"), List.of("refresh_token"), List.of("token"));
    }

    @Test
    public void shouldReturnResponseWithExceptionWhenRestTemplateDoThrowOnRefreshToken() {
        when(restTemplate.postForEntity(eq("localhost:8080/protocol/openid-connect/token"), any(), eq(String.class))).thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN, "Forbidden"));
        final ResponseEntity<?> response = authenticationKeycloakManager.refresh("refresh_token");
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        Assertions.assertThat(response.getBody()).isEqualTo("403 Forbidden");
    }

}
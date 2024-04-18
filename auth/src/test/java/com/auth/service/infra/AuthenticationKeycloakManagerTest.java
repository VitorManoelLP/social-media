package com.auth.service.infra;

import com.auth.service.domain.UserLogin;
import com.auth.service.domain.UserRepresentation;
import com.auth.service.domain.UserSignUp;
import com.auth.service.fixture.ClientContextHolderFixture;
import com.auth.service.fixture.KeycloakPropertiesFixture;
import com.auth.service.fixture.UserFixture;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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

    @Captor
    private ArgumentCaptor<HttpEntity<UserRepresentation>> captorUser;

    @BeforeAll
    public static void init() {
        ClientContextHolderFixture.mockContextHolder();
    }

    @BeforeEach
    public void setup() {
        authenticationKeycloakManager = new AuthenticationKeycloakManager(restTemplate);
    }

    @AfterAll
    public static void afterAll() {
        ClientContextHolderFixture.clearContextHolder();
    }

    @Test
    public void shouldLoginWithRightHeaders() {

        when(restTemplate.postForEntity(eq("localhost:8080/realms/realm/protocol/openid-connect/token"), any(), eq(String.class))).thenReturn(ResponseEntity.ok("token"));

        final ResponseEntity<?> response = authenticationKeycloakManager.login(new UserLogin("username", "password"));

        verify(restTemplate, atLeastOnce()).postForEntity(eq("localhost:8080/realms/realm/protocol/openid-connect/token"), captor.capture(), eq(String.class));

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
        when(restTemplate.postForEntity(eq("localhost:8080/realms/realm/protocol/openid-connect/token"), any(), eq(String.class))).thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN, "Forbidden"));
        final ResponseEntity<?> response = authenticationKeycloakManager.login(new UserLogin("username", "password"));
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        Assertions.assertThat(response.getBody()).isEqualTo("403 Forbidden");
    }

    @Test
    public void shouldRefreshTokenWithRightHeaders() {

        when(restTemplate.postForEntity(eq("localhost:8080/realms/realm/protocol/openid-connect/token"), any(), eq(String.class))).thenReturn(ResponseEntity.ok("token"));

        final ResponseEntity<?> response = authenticationKeycloakManager.refresh("token");

        verify(restTemplate, atLeastOnce()).postForEntity(eq("localhost:8080/realms/realm/protocol/openid-connect/token"), captor.capture(), eq(String.class));

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
        when(restTemplate.postForEntity(eq("localhost:8080/realms/realm/protocol/openid-connect/token"), any(), eq(String.class))).thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN, "Forbidden"));
        final ResponseEntity<?> response = authenticationKeycloakManager.refresh("refresh_token");
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        Assertions.assertThat(response.getBody()).isEqualTo("403 Forbidden");
    }

    @Test
    public void shouldCreateUserWithRightPayload() {

        when(restTemplate.postForEntity(eq("localhost:8080/admin/realms/realm/users"), any(HttpEntity.class), eq(Void.class))).thenReturn(ResponseEntity.ok().build());

        final UserSignUp userSignUp = UserFixture.createUserSignUp();

        final ResponseEntity<?> response = authenticationKeycloakManager.create(userSignUp);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(restTemplate, atLeastOnce()).postForEntity(eq("localhost:8080/admin/realms/realm/users"), captorUser.capture(), eq(Void.class));

        final HttpEntity<UserRepresentation> value = captorUser.getValue();

        final UserRepresentation body = value.getBody();

        HttpHeaders headers = value.getHeaders();

        Assertions.assertThat(headers).isNotNull();
        Assertions.assertThat(headers.get("Authorization")).isNotNull().isEqualTo(List.of("Bearer token"));

        Assertions.assertThat(body).isNotNull();
        Assertions.assertThat(body.getEmail()).isEqualTo(userSignUp.email());
        Assertions.assertThat(body.getUsername()).isEqualTo(userSignUp.username());
        Assertions.assertThat(body.isEmailVerified()).isFalse();
        Assertions.assertThat(body.isEnabled()).isTrue();

        Assertions.assertThat(body.getCredentials())
                .extracting(
                        UserRepresentation.CredentialRepresentation::getType,
                        UserRepresentation.CredentialRepresentation::getValue,
                        UserRepresentation.CredentialRepresentation::isTemporary
                ).containsOnly(
                        Tuple.tuple("password",
                                userSignUp.password(),
                                false)
                );

    }

    @Test
    public void shouldReturnResponseWithExceptionWhenRestTemplateDoThrowOnCreate() {
        when(restTemplate.postForEntity(eq("localhost:8080/admin/realms/realm/users"), any(), eq(Void.class))).thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN, "Forbidden"));
        final ResponseEntity<?> response = authenticationKeycloakManager.create(UserFixture.createUserSignUp());
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        Assertions.assertThat(response.getBody()).isEqualTo("403 Forbidden");
    }
}
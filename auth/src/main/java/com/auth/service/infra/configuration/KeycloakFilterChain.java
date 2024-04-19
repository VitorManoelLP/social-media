package com.auth.service.infra.configuration;

import com.auth.service.application.exception.InvalidClientAccessException;
import com.auth.service.domain.KeycloakClient;
import com.auth.service.infra.context.ClientContextHolder;
import com.auth.service.infra.property.KeycloakHeadersBuilder;
import com.auth.service.infra.property.KeycloakProperties;
import com.auth.service.infra.util.JwtToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class KeycloakFilterChain extends GenericFilterBean {

    private final String adminId;
    private final String adminSecret;
    private final RestTemplate restTemplate;
    private final KeycloakProperties keycloakProperties;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final ClientContextHolder instance = ClientContextHolder.getInstance();
        defineClient(instance);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void defineClient(ClientContextHolder instance) {

        try {

            final HttpEntity<MultiValueMap<String, String>> httpEntity = KeycloakHeadersBuilder.builder(adminId, adminSecret)
                    .withGrantType("client_credentials")
                    .build();

            final ResponseEntity<String> responseToken = restTemplate.postForEntity(
                    keycloakProperties.getTokenEndpoint("master"),
                    httpEntity,
                    String.class
            );

            if (responseToken.getStatusCode().is2xxSuccessful()) {
                instance.setClient(new KeycloakClient(JwtToken.from(responseToken), keycloakProperties));
            }

        } catch (HttpClientErrorException ex) {
            throw new InvalidClientAccessException(ex.getStatusCode(), ex.getMessage());
        }
    }

}

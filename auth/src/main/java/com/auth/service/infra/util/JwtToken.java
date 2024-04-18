package com.auth.service.infra.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.Value;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Map;

@Value
public class JwtToken {

    DecodedJWT jwt;
    String token;

    public JwtToken(String token) {
        this.token = token;
        jwt = JWT.decode(token);
    }

    @SneakyThrows
    public static JwtToken from(ResponseEntity<String> responseEntity) {
        return new JwtToken((String) new ObjectMapper().readValue(responseEntity.getBody(), Map.class).get("access_token"));
    }

    public boolean isExpired() {
        return jwt.getExpiresAt().before(new Date());
    }

}

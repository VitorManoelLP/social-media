package com.auth.service.application;

import com.auth.service.domain.UserLogin;
import com.auth.service.domain.UserSignUp;
import org.springframework.http.ResponseEntity;

public interface AuthenticationManager {
    ResponseEntity<?> login(UserLogin user);
    ResponseEntity<?> refresh(String refreshToken);
    ResponseEntity<?> create(UserSignUp userSignUp);
}

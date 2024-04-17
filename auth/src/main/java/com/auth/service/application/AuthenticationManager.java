package com.auth.service.application;

import com.auth.service.domain.UserLogin;
import org.springframework.http.ResponseEntity;

public interface AuthenticationManager {
    ResponseEntity<?> login(UserLogin user);
    ResponseEntity<?> refresh(String refreshToken);
}

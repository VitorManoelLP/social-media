package com.auth.service.application;

import com.auth.service.domain.User;
import org.springframework.http.ResponseEntity;

public interface AuthenticationManager {
    ResponseEntity<?> login(User user);
    ResponseEntity<?> refresh(String refreshToken);
}

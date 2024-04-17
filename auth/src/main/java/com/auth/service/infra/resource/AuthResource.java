package com.auth.service.infra.resource;

import com.auth.service.application.AuthenticationManager;
import com.auth.service.domain.UserLogin;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthResource {

    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLogin user) {
        return authenticationManager.login(user);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> login(@RequestParam("refresh_token") String refreshToken) {
        return authenticationManager.refresh(refreshToken);
    }

}

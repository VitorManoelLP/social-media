package com.auth.service.domain;

import com.auth.service.application.exception.WrongConfirmPasswordException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserSignUp(@NotBlank String username, @Email @NotBlank String email, @NotBlank String password, @NotBlank String confirmPassword) {
    public UserSignUp {
        if (!password.equals(confirmPassword)) {
            throw new WrongConfirmPasswordException("The confirm password field must match the password.");
        }
    }
}

package com.auth.service.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class UserRepresentation {

    boolean emailVerified = false;
    boolean enabled = true;

    @Email
    @NotBlank
    String email;

    @NotBlank
    String username;

    List<CredentialRepresentation> credentials;

    public static UserRepresentation of(UserSignUp userSignUp) {
        return new UserRepresentation(userSignUp.email(), userSignUp.username(), List.of(new CredentialRepresentation(userSignUp.password())));
    }

    @Value
    public static class CredentialRepresentation {

        boolean temporary = false;
        String type = "password";
        String value;

    }


}

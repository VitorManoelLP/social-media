package com.auth.service.domain;

import com.auth.service.application.exception.WrongConfirmPasswordException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserSignUpTest {

    @Test
    public void shouldThrowWhenPasswordDiffersFromConfirmPassword() {
        Assertions.assertThatThrownBy(() -> new UserSignUp("username", "email@gmail.com", "12345", "1234567"))
                .isInstanceOf(WrongConfirmPasswordException.class)
                .hasMessage("The confirm password field must match the password.");
    }

    @Test
    public void shouldCreate() {
        Assertions.assertThatCode(() -> new UserSignUp("username", "email@gmail.com", "1234567", "1234567"))
                .doesNotThrowAnyException();
    }


}

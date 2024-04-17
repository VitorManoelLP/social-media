package com.auth.service.domain;

import jakarta.validation.constraints.NotBlank;

public record User(@NotBlank String username, @NotBlank String password) {
}

package com.auth.service.domain;

import jakarta.validation.constraints.NotBlank;

public record UserLogin(@NotBlank String username, @NotBlank String password) {
}

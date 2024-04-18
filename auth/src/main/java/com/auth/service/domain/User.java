package com.auth.service.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString(of = "email")
@EqualsAndHashCode(of = "email")
@Table(name = "user", schema = "auth")
@AllArgsConstructor
public class User {

    @Id
    @Email
    private String email;

    @NotBlank
    private String username;

}

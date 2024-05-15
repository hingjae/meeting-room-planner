package com.honey.meetingroomplanner.user.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserForm {
    @NotBlank(message = "{NotBlank.createUserForm.username}")
    private final String username;

    @NotNull(message = "{NotNull}")
    private final Long teamId;

    @NotBlank(message = "{NotBlank}")
    private final String password;

    @NotBlank(message = "{NotBlank}")
    private final String name;
}

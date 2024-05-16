package com.honey.meetingroomplanner.user.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateUserForm {
    @NotBlank(message = "{NotNull}")
    private final String username;

    @NotNull(message = "{NotNull}")
    private final Long teamId;

    @NotBlank(message = "{NotBlank}")
    private final String password;

    @NotBlank(message = "{NotBlank}")
    private final String name;

    @Builder
    public CreateUserForm(String username, Long teamId, String password, String name) {
        this.username = username;
        this.teamId = teamId;
        this.password = password;
        this.name = name;
    }
}

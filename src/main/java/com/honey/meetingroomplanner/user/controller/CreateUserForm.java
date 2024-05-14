package com.honey.meetingroomplanner.user.controller;

import com.honey.meetingroomplanner.user.entity.RoleType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserForm {
    private final String username;

    private final Long teamId;

    private final String password;

    private final String name;

    private final RoleType roleType;
}

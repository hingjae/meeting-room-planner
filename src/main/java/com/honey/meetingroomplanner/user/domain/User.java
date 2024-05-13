package com.honey.meetingroomplanner.user.domain;

import com.honey.meetingroomplanner.team.domain.Team;
import com.honey.meetingroomplanner.user.RoleType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class User {
    private final String username;

    private final Team team;

    private final String password;

    private final String name;

    private final RoleType roleType;

    @Builder
    public User(String username, Team team, String password, String name, RoleType roleType) {
        this.username = username;
        this.team = team;
        this.password = password;
        this.name = name;
        this.roleType = roleType;
    }
}

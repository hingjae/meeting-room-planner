package com.honey.meetingroomplanner.user.controller;

import com.honey.meetingroomplanner.team.entity.Team;
import lombok.Getter;

@Getter
public class TeamResponse {
    private final Long id;

    private final String name;

    public TeamResponse(Team team) {
        this.id = team.getId();
        this.name = team.getName();
    }
}

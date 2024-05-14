package com.honey.meetingroomplanner.team.controller;

import com.honey.meetingroomplanner.team.entity.Team;
import lombok.Getter;

@Getter
public class CreateTeamForm {
    private final String name;

    public CreateTeamForm(String name) {
        this.name = name;
    }

    public Team toEntity() {
        return Team.builder()
                .name(name)
                .build();
    }
}

package com.honey.meetingroomplanner.team.controller;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ModifyTeamForm {
    private final String name;

    @Builder
    public ModifyTeamForm(String name) {
        this.name = name;
    }
}

package com.honey.meetingroomplanner.team.domain;

import com.honey.meetingroomplanner.team.domain.dto.CreateTeam;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Team {
    private final Long id;

    private final String name;

    @Builder
    public Team(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Team from(CreateTeam createTeam) {
        return Team.builder()
                .name(createTeam.getName())
                .build();
    }
}

package com.honey.meetingroomplanner.team.domain;

import com.honey.meetingroomplanner.team.domain.dto.CreateTeam;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TeamTest {

    @Test
    public void CreateTeam으로Team을_생성한다() {
        CreateTeam createTeam = CreateTeam.builder()
                .name("teamA")
                .build();

        Team team = Team.from(createTeam);

        Assertions.assertThat(team.getName()).isEqualTo(createTeam.getName());
    }
}
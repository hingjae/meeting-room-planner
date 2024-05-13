package com.honey.meetingroomplanner.team.service;

import com.honey.meetingroomplanner.mock.FakeTeamRepository;
import com.honey.meetingroomplanner.team.domain.Team;
import com.honey.meetingroomplanner.team.domain.dto.CreateTeam;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TeamServiceImplTest {

    private TeamServiceImpl teamServiceImpl;

    @BeforeEach
    public void init() {
        FakeTeamRepository fakeTeamRepository = new FakeTeamRepository();
        teamServiceImpl = TeamServiceImpl.builder()
                .teamRepository(fakeTeamRepository)
                .build();
    }

    @Test
    public void CreateTeam으로Team을_생성한다() {
        CreateTeam createTeam = CreateTeam.builder()
                .name("newTeam")
                .build();

        Team team = teamServiceImpl.create(createTeam);

        Assertions.assertThat(team.getId()).isNotNull();
        Assertions.assertThat(team.getName()).isEqualTo(createTeam.getName());
    }
}
package com.honey.meetingroomplanner.mock;

import com.honey.meetingroomplanner.team.domain.Team;
import com.honey.meetingroomplanner.team.service.port.TeamRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class FakeTeamRepository implements TeamRepository {

    private final AtomicLong autoGeneratedId = new AtomicLong(0);

    private final List<Team> data = new ArrayList<>();
    @Override
    public Team save(Team team) {
        if (team.getId() == null || team.getId() == 0) {
            Team newTeam = Team.builder()
                    .id(autoGeneratedId.incrementAndGet())
                    .name(team.getName())
                    .build();
            data.add(newTeam);
            return newTeam;
        } else {
            data.removeIf(item -> Objects.equals(item.getId(), team.getId()));
            data.add(team);
            return team;
        }
    }
}
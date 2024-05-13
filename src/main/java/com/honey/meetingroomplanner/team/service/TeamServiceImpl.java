package com.honey.meetingroomplanner.team.service;

import com.honey.meetingroomplanner.team.controller.port.TeamService;
import com.honey.meetingroomplanner.team.domain.Team;
import com.honey.meetingroomplanner.team.domain.dto.CreateTeam;
import com.honey.meetingroomplanner.team.service.port.TeamRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Builder
@RequiredArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    @Override
    public Team create(CreateTeam createTeam) {
        return teamRepository.save(Team.from(createTeam));
    }
}

package com.honey.meetingroomplanner.team.infrastructure;

import com.honey.meetingroomplanner.team.domain.Team;
import com.honey.meetingroomplanner.team.service.port.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TeamRepositoryImpl implements TeamRepository {

    private final TeamJpaRepository teamJpaRepository;

    @Override
    public Team save(Team team) {
        return teamJpaRepository.save(TeamEntity.from(team))
                .toModel();
    }
}

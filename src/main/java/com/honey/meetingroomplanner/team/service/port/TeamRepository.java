package com.honey.meetingroomplanner.team.service.port;

import com.honey.meetingroomplanner.team.domain.Team;

public interface TeamRepository {
    Team save(Team team);
}

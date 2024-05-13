package com.honey.meetingroomplanner.team.controller.port;

import com.honey.meetingroomplanner.team.domain.Team;
import com.honey.meetingroomplanner.team.domain.dto.CreateTeam;

public interface TeamService {
    Team create(CreateTeam createTeam);
}

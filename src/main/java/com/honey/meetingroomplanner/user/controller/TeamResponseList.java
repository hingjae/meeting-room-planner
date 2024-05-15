package com.honey.meetingroomplanner.user.controller;

import com.honey.meetingroomplanner.team.entity.Team;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class TeamResponseList {
    private List<TeamResponse> items;

    public static TeamResponseList from(List<Team> teams) {
        return TeamResponseList.builder()
                .items(
                        teams.stream()
                                .map(TeamResponse::new)
                                .collect(Collectors.toList())
                )
                .build();
    }
}

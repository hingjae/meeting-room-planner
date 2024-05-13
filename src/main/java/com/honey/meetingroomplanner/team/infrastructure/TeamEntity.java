package com.honey.meetingroomplanner.team.infrastructure;

import com.honey.meetingroomplanner.team.domain.Team;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Table(name = "team")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamEntity {
    @Id
    private Long id;

    private String name;

    public static TeamEntity from(Team team) {
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setId(team.getId());
        teamEntity.setName(team.getName());
        return teamEntity;
    }

    public Team toModel() {
        return Team.builder()
                .id(id)
                .name(name)
                .build();
    }
}

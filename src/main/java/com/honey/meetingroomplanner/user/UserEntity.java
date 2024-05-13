package com.honey.meetingroomplanner.user;

import com.honey.meetingroomplanner.common.BaseTimeEntity;
import com.honey.meetingroomplanner.team.infrastructure.TeamEntity;
import com.honey.meetingroomplanner.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@Setter
@Table(name = "user")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseTimeEntity {
    @Id
    private String username;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_id")
    private TeamEntity teamEntity;

    private String password;

    private String name;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    public static UserEntity from(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setTeamEntity(TeamEntity.from(user.getTeam()));
        userEntity.setPassword(user.getPassword());
        userEntity.setName(user.getName());
        userEntity.setRoleType(user.getRoleType());
        return userEntity;
    }

    public User toModel() {
        return User.builder()
                .username(username)
                .team(teamEntity.toModel())
                .password(password)
                .name(name)
                .roleType(roleType)
                .build();
    }
}

package com.honey.meetingroomplanner.user.entity;

import com.honey.meetingroomplanner.common.BaseTimeEntity;
import com.honey.meetingroomplanner.team.entity.Team;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import static jakarta.persistence.FetchType.LAZY;

@Table(name = "users")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity implements Persistable<String> {
    @Id
    private String username;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    private String password;

    private String name;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Builder
    public User(String username, Team team, String password, String name, RoleType roleType) {
        this.username = username;
        this.team = team;
        this.password = password;
        this.name = name;
        this.roleType = roleType;
    }

    @Override
    public String getId() {
        return this.username;
    }

    @Override
    public boolean isNew() {
        return this.createdDate == null;
    }
}

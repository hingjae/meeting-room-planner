package com.honey.meetingroomplanner.team.repository;

import com.honey.meetingroomplanner.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}

package com.honey.meetingroomplanner.team.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamJpaRepository extends JpaRepository<TeamEntity, Long> {
}

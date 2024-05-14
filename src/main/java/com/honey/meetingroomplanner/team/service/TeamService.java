package com.honey.meetingroomplanner.team.service;

import com.honey.meetingroomplanner.team.controller.CreateTeamForm;
import com.honey.meetingroomplanner.team.controller.ModifyTeamForm;
import com.honey.meetingroomplanner.team.entity.Team;
import com.honey.meetingroomplanner.team.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TeamService {
    private final TeamRepository teamRepository;

    @Transactional
    public Long create(CreateTeamForm form) {
        return teamRepository.save(form.toEntity())
                .getId();
    }

    public Team findById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Transactional
    public void update(Long id, ModifyTeamForm form) {
        Team team = findById(id);

        team.setName(form.getName());
    }
}

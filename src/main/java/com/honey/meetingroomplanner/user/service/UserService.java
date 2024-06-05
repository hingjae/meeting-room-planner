package com.honey.meetingroomplanner.user.service;

import com.honey.meetingroomplanner.team.entity.Team;
import com.honey.meetingroomplanner.team.service.TeamService;
import com.honey.meetingroomplanner.user.controller.CreateUserForm;
import com.honey.meetingroomplanner.user.entity.RoleType;
import com.honey.meetingroomplanner.user.entity.User;
import com.honey.meetingroomplanner.user.exception.UsernameAlreadyExistsException;
import com.honey.meetingroomplanner.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final TeamService teamService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String create(CreateUserForm form) {
        validDuplicateUsername(form.getUsername());
        Team team = teamService.findById(form.getTeamId());
        String encodedPassword = passwordEncoder.encode(form.getPassword());

        User user = User.builder()
                .username(form.getUsername())
                .team(team)
                .password(encodedPassword)
                .name(form.getName())
                .roleType(RoleType.USER)
                .build();

        return userRepository.save(user)
                 .getUsername();
    }

    private void validDuplicateUsername(String username) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isPresent()) {
            throw new UsernameAlreadyExistsException();
        }
    }
}

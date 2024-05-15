package com.honey.meetingroomplanner.user.service;

import com.honey.meetingroomplanner.team.entity.Team;
import com.honey.meetingroomplanner.team.repository.TeamRepository;
import com.honey.meetingroomplanner.user.controller.CreateUserForm;
import com.honey.meetingroomplanner.user.entity.RoleType;
import com.honey.meetingroomplanner.user.entity.User;
import com.honey.meetingroomplanner.user.exception.UsernameAlreadyExistsException;
import com.honey.meetingroomplanner.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private EntityManager em;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Test
    public void 유저를_생성한다() {
        Team team = initTeam();
        CreateUserForm form = getCreateUserForm(team, "username");

        given(passwordEncoder.encode("newPassword")).willReturn("encodedPassword");
        String username = userService.create(form);
        em.flush();
        em.clear();

        User user = userRepository.findById(username).get();
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("username");
        assertThat(user.getTeam().getName()).isEqualTo("team");
        assertThat(user.getPassword()).isEqualTo("encodedPassword");
        assertThat(user.getName()).isEqualTo("newName");
        assertThat(user.getRoleType()).isEqualTo(RoleType.USER);
    }

    private static CreateUserForm getCreateUserForm(Team team, String username) {
        return CreateUserForm.builder()
                .username(username)
                .teamId(team.getId())
                .password("newPassword")
                .name("newName")
                .build();
    }

    private Team initTeam() {
        return teamRepository.save(Team.builder()
                .name("team")
                .build());
    }

    @Transactional
    @Test
    public void 유저를생성할떄_username이이미존재하면_예외를던진다() {
        Team team = initTeam();
        userService.create(getCreateUserForm(team, "username"));
        em.flush();
        em.clear();

        CreateUserForm form = getCreateUserForm(team, "username");
        assertThatThrownBy(() -> userService.create(form))
                .isInstanceOf(UsernameAlreadyExistsException.class);
    }
}
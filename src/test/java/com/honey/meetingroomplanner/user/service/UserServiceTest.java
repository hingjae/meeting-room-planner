package com.honey.meetingroomplanner.user.service;

import com.honey.meetingroomplanner.team.entity.Team;
import com.honey.meetingroomplanner.team.repository.TeamRepository;
import com.honey.meetingroomplanner.user.controller.CreateUserForm;
import com.honey.meetingroomplanner.user.entity.RoleType;
import com.honey.meetingroomplanner.user.entity.User;
import com.honey.meetingroomplanner.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
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
        Team team = teamRepository.save(Team.builder()
                        .name("team")
                        .build());

        CreateUserForm form = CreateUserForm.builder()
                .username("newUser")
                .teamId(team.getId())
                .password("newPassword")
                .name("newName")
                .roleType(RoleType.USER)
                .build();

        given(passwordEncoder.encode("newPassword")).willReturn("encodedPassword");
        String username = userService.create(form);
        em.flush();
        em.clear();

        User user = userRepository.findById(username).get();

        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("newUser");
        assertThat(user.getTeam().getName()).isEqualTo("team");
        assertThat(user.getPassword()).isEqualTo("encodedPassword");
        assertThat(user.getName()).isEqualTo("newName");
        assertThat(user.getRoleType()).isEqualTo(RoleType.USER);
    }
}
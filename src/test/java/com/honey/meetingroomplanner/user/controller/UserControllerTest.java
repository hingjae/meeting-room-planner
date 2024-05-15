package com.honey.meetingroomplanner.user.controller;

import com.honey.meetingroomplanner.team.entity.Team;
import com.honey.meetingroomplanner.team.service.TeamService;
import com.honey.meetingroomplanner.user.exception.UsernameAlreadyExistsException;
import com.honey.meetingroomplanner.user.service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamService teamService;
    @MockBean
    private UserService userService;

    @Test
    public void 회원가입폼을_반환한다() throws Exception {
        given(teamService.findAll()).willReturn(getTeams());

        mockMvc.perform(get("/users/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("teams"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("user/sign-up"));
    }

    private List<Team> getTeams() {
        Team teamA = Team.builder()
                .id(1L)
                .name("teamA")
                .build();

        Team teamB = Team.builder()
                .id(2L)
                .name("teamB")
                .build();

        return List.of(teamA, teamB);
    }

    @Test
    public void 회원가입을_한다() throws Exception {
        CreateUserForm form = getCreateUserForm();
        CsrfToken csrfToken = getCsrfToken();

        mockMvc.perform(post("/users/new")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", form.getUsername())
                        .param("password", form.getPassword())
                        .param("name", form.getName())
                        .param("teamId", String.valueOf(form.getTeamId()))
                        .param(csrfToken.getParameterName(), csrfToken.getToken())
                        .with(csrf().asHeader())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    public void username이_null이면_예외를던진다() throws Exception {
        CreateUserForm form = getCreateUserForm();
        CsrfToken csrfToken = getCsrfToken();

        mockMvc.perform(post("/users/new")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("password", form.getPassword())
                        .param("name", form.getName())
                        .param("teamId", String.valueOf(form.getTeamId()))
                        .param(csrfToken.getParameterName(), csrfToken.getToken())
                        .with(csrf().asHeader())
                )
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("user", "username"))
                .andExpect(model().attributeHasFieldErrorCode("user", "username", "NotBlank")) // @NotBlank 에러 코드 확인
                .andExpect(view().name("user/sign-up"));
    }

    @Test
    public void username이_빈문자열이면_예외를던진다() throws Exception {
        CreateUserForm form = getCreateUserForm();
        CsrfToken csrfToken = getCsrfToken();

        mockMvc.perform(post("/users/new")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "")
                        .param("password", form.getPassword())
                        .param("name", form.getName())
                        .param("teamId", String.valueOf(form.getTeamId()))
                        .param(csrfToken.getParameterName(), csrfToken.getToken())
                        .with(csrf().asHeader())
                )
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("user", "username"))
                .andExpect(model().attributeHasFieldErrorCode("user", "username", "NotBlank")) // @NotBlank 에러 코드 확인
                .andExpect(view().name("user/sign-up"));
    }

    @Disabled
    @Test
    public void username이_이미존재하면_예외를던진다() throws Exception {
        CreateUserForm form = getCreateUserForm();
        CsrfToken csrfToken = getCsrfToken();

        given(userService.create(any())).willThrow(new UsernameAlreadyExistsException());

        mockMvc.perform(post("/users/new")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", form.getUsername())
                        .param("password", form.getPassword())
                        .param("name", form.getName())
                        .param("teamId", String.valueOf(form.getTeamId()))
                        .param(csrfToken.getParameterName(), csrfToken.getToken())
                        .with(csrf().asHeader())
                )
                .andExpect(view().name("user/sign-up")) // 예외가 발생하여 다시 회원가입 페이지로 리다이렉트할 것으로 예상됩니다.
                .andExpect(model().attributeExists("errorMessage")); // 모델에 errorMessage 속성이 존재할 것으로 예상됩니다.
    }

    private CsrfToken getCsrfToken() {
        HttpSessionCsrfTokenRepository csrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = csrfTokenRepository.generateToken(new MockHttpServletRequest());
        return csrfToken;
    }

    private CreateUserForm getCreateUserForm() {
        return CreateUserForm.builder()
                .username("username")
                .password("password")
                .teamId(1L)
                .name("name")
                .build();
    }
}

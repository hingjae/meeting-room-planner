package com.honey.meetingroomplanner.meeting.controller;

import com.honey.meetingroomplanner.meeting.dto.CreateMeetingForm;
import com.honey.meetingroomplanner.meeting.service.MeetingService;
import com.honey.meetingroomplanner.mettingroom.entity.MeetingRoom;
import com.honey.meetingroomplanner.mettingroom.service.MeetingRoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class MeetingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeetingRoomService meetingRoomService;
    @MockBean
    private MeetingService meetingService;

    private static final Long MEETING_ROOM_ID = 1L;
    @Test
    @WithMockUser
    public void 회의를_생성폼을가져온다() throws Exception {
        given(meetingRoomService.findAll()).willReturn(getMeetingRooms());

        mockMvc.perform(get("/meeting/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("teams"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("meeting/create-meeting-form"));
    }

    @Test
    @WithMockUser
    public void 회의를_생성한다() throws Exception {
        Long meetingId = 1L;
        CreateMeetingForm form = getCreateMeetingForm();
        CsrfToken csrfToken = getCsrfToken();
        given(meetingService.save(form)).willReturn(meetingId);

        mockMvc.perform(post("/meeting/new")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("meetingRoomId", String.valueOf(form.getMeetingRoomId()))
                        .param("startTime", String.valueOf(form.getStartTime()))
                        .param("endTime", String.valueOf(form.getEndTime()))
                        .param("title", form.getTitle())
                        .param(csrfToken.getParameterName(), csrfToken.getToken())
                        .with(csrf().asHeader())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    private CreateMeetingForm getCreateMeetingForm() {
        return CreateMeetingForm.builder()
                .meetingRoomId(MEETING_ROOM_ID)
                .startTime(LocalDateTime.of(2024, 5, 28, 12, 0, 0))
                .endTime(LocalDateTime.of(2024, 5, 28, 13, 0, 0))
                .title("title")
                .build();
    }

    private CsrfToken getCsrfToken() {
        HttpSessionCsrfTokenRepository csrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = csrfTokenRepository.generateToken(new MockHttpServletRequest());
        return csrfToken;
    }

    private List<MeetingRoom> getMeetingRooms() {
        MeetingRoom meetingRoom1 = MeetingRoom.builder()
                .id(1L)
                .name("meetingRoom1").build();
        MeetingRoom meetingRoom2 = MeetingRoom.builder()
                .id(2L)
                .name("meetingRoom2").build();
        return List.of(meetingRoom1, meetingRoom2);
    }
}
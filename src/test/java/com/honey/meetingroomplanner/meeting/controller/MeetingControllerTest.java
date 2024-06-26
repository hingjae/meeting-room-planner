package com.honey.meetingroomplanner.meeting.controller;

import com.honey.meetingroomplanner.meeting.controller.dto.CreateMeetingForm;
import com.honey.meetingroomplanner.meeting.entity.Meeting;
import com.honey.meetingroomplanner.meeting.exception.MeetingRoomAlreadyBookedException;
import com.honey.meetingroomplanner.meeting.service.MeetingService;
import com.honey.meetingroomplanner.mettingroom.entity.MeetingRoom;
import com.honey.meetingroomplanner.mettingroom.service.MeetingRoomService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
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

    @Disabled
    @Test
    @WithMockUser
    public void 회의시간이곂치면_에러메세지를출력한다() throws Exception {
        CreateMeetingForm form = getCreateMeetingForm();
        CsrfToken csrfToken = getCsrfToken();

        given(meetingService.save(form)).willThrow(new MeetingRoomAlreadyBookedException());

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
                .andExpect(redirectedUrl("/meeting/new"))
                .andExpect(flash().attributeExists("errorMessage"));
    }

    @Test
    @WithMockUser
    public void 날짜로_회의를_조회한다() throws Exception {
        LocalDate date = LocalDate.of(2024, 6, 6);
        List<Meeting> meetings = getMeetings();
        given(meetingService.findByDate(date)).willReturn(meetings);

        mockMvc.perform(get("/api/meetings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("date", String.valueOf(date))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.items").exists())
                .andExpect(jsonPath("$.items[0].title").value("meeting1"))
                .andExpect(jsonPath("$.items[1].title").value("meeting2"))
                .andExpect(jsonPath("$.items[0].meetingRoomName").value("meetingRoom1"))
                .andExpect(jsonPath("$.items[1].meetingRoomName").value("meetingRoom1"));
    }

    private List<Meeting> getMeetings() {
        MeetingRoom meetingRoom = MeetingRoom.builder()
                .id(1L)
                .name("meetingRoom1")
                .build();

        Meeting meeting1 = Meeting.builder()
                .id(1L)
                .meetingRoom(meetingRoom)
                .title("meeting1")
                .startTime(LocalDateTime.of(20204, 6, 6, 12, 0, 0))
                .endTime(LocalDateTime.of(20204, 6, 6, 15, 0, 0))
                .build();

        Meeting meeting2 = Meeting.builder()
                .id(2L)
                .meetingRoom(meetingRoom)
                .title("meeting2")
                .startTime(LocalDateTime.of(20204, 6, 6, 17, 0, 0))
                .endTime(LocalDateTime.of(20204, 6, 6, 19, 0, 0))
                .build();

        return List.of(meeting1, meeting2);
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
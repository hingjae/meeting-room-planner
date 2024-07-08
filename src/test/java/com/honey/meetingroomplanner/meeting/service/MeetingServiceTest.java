package com.honey.meetingroomplanner.meeting.service;

import com.honey.meetingroomplanner.meeting.controller.dto.CreateMeetingForm;
import com.honey.meetingroomplanner.meeting.entity.Meeting;
import com.honey.meetingroomplanner.meeting.exception.MeetingRoomAlreadyBookedException;
import com.honey.meetingroomplanner.meeting.repository.MeetingRepository;
import com.honey.meetingroomplanner.mettingroom.entity.MeetingRoom;
import com.honey.meetingroomplanner.mettingroom.repository.MeetingRoomRepository;
import com.honey.meetingroomplanner.security.dto.CustomUserDetails;
import com.honey.meetingroomplanner.team.entity.Team;
import com.honey.meetingroomplanner.team.repository.TeamRepository;
import com.honey.meetingroomplanner.user.entity.RoleType;
import com.honey.meetingroomplanner.user.entity.User;
import com.honey.meetingroomplanner.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class MeetingServiceTest {

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    public static final String MEETING_ROOM_NAME = "meetingRoom1";

    @Test
    public void 예약을_생성한다() {
        MeetingRoom meetingRoom = MeetingRoom.builder()
                .name(MEETING_ROOM_NAME)
                .build();

        meetingRoomRepository.save(meetingRoom);

        LocalDateTime startTime = LocalDateTime.of(2024, 5, 27, 14, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 5, 27, 15, 0, 0);
        Long meetingRoomId = meetingRoomRepository.findByName(MEETING_ROOM_NAME).getId();
        String title = "something like that";
        CreateMeetingForm form = CreateMeetingForm.builder()
                .startTime(startTime)
                .endTime(endTime)
                .meetingRoomId(meetingRoomId)
                .title(title)
                .build();

        Long savedId = meetingService.save(form);

        Meeting meeting = meetingRepository.findById(savedId).orElseThrow(EntityNotFoundException::new);
        assertThat(meeting).isNotNull();
        assertThat(meeting.getStartTime()).isEqualTo(startTime);
        assertThat(meeting.getEndTime()).isEqualTo(endTime);
        assertThat(meeting.getMeetingRoom().getId()).isEqualTo(meetingRoomId);
        assertThat(meeting.getTitle()).isEqualTo(title);
    }

    @Test
    public void 예약시간이_곂치면_예외를던진다() {
        MeetingRoom meetingRoom = MeetingRoom.builder()
                .name(MEETING_ROOM_NAME)
                .build();

        meetingRoomRepository.save(meetingRoom);

        saveMeeting(meetingRoom, "meeting1", LocalDateTime.of(2024, 5, 27, 14, 0, 0), LocalDateTime.of(2024, 5, 27, 18, 0, 0));
        LocalDateTime startTime = LocalDateTime.of(2024, 5, 27, 15, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 5, 27, 17, 0, 0);
        CreateMeetingForm form = getCreateMeetingForm(meetingRoom.getId(), startTime, endTime);

        assertThatThrownBy(() -> meetingService.save(form))
                .isInstanceOf(MeetingRoomAlreadyBookedException.class);
    }

    @DisplayName("날짜로 회의를 조회한다.")
    @Test
    public void 날짜로_회의를조회한다() {
        initData();

        LocalDate dateParam = LocalDate.of(2024, 6, 6);
        List<Meeting> meetings = meetingService.findByDate(dateParam);

        assertThat(meetings).hasSize(2);
        assertThat(meetings.get(0).getTitle()).isEqualTo("meeting1");
        assertThat(meetings.get(1).getTitle()).isEqualTo("meeting2");
    }


    private void initData() {
        MeetingRoom meetingRoom = MeetingRoom.builder()
                .name("meetingRoom1")
                .build();

        MeetingRoom savedMeetingRoom = meetingRoomRepository.save(meetingRoom);

        Meeting meeting1 = Meeting.builder()
                .meetingRoom(savedMeetingRoom)
                .title("meeting1")
                .startTime(LocalDateTime.of(2024, 6, 6, 13, 0, 0))
                .endTime(LocalDateTime.of(2024, 6, 6, 15, 0, 0))
                .build();

        Meeting meeting2 = Meeting.builder()
                .meetingRoom(savedMeetingRoom)
                .title("meeting2")
                .startTime(LocalDateTime.of(2024, 6, 6, 15, 0, 0))
                .endTime(LocalDateTime.of(2024, 6, 6, 17, 0, 0))
                .build();


        Meeting meeting3 = Meeting.builder()
                .meetingRoom(savedMeetingRoom)
                .title("meeting3")
                .startTime(LocalDateTime.of(2024, 6, 7, 15, 0, 0))
                .endTime(LocalDateTime.of(2024, 6, 7, 17, 0, 0))
                .build();

        meetingRepository.save(meeting1);
        meetingRepository.save(meeting2);
        meetingRepository.save(meeting3);
    }

    private void saveMeeting(MeetingRoom meetingRoom, String title, LocalDateTime startTime, LocalDateTime endTime) {
        Meeting meeting = Meeting.builder()
                .meetingRoom(meetingRoom)
                .title(title)
                .startTime(startTime)
                .endTime(endTime)
                .build();
        meetingRepository.save(meeting);
    }

    private CreateMeetingForm getCreateMeetingForm(Long meetingRoomId, LocalDateTime startTime, LocalDateTime endTime) {
        return CreateMeetingForm.builder()
                .meetingRoomId(meetingRoomId)
                .title("meeting2")
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

    @Transactional
    @DisplayName("회원정보와 예약요청 폼으로 예약을 생성한다.")
    @Test
    public void CreateMeeting() {
        LocalDateTime startTime = LocalDateTime.of(2024, 6, 6, 13, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 6, 6, 15, 0, 0);
        String title = "meeting1";
        Team team = teamRepository.save(getTeam());
        User user = userRepository.save(getUser(team));
        MeetingRoom meetingRoom = meetingRoomRepository.save(getMeetingRoom());
        CustomUserDetails userDetails = CustomUserDetails.from(user);
        CreateMeetingForm form = getCreateMeetingForm(meetingRoom.getId(), startTime, endTime, title);
        flushAndClear();

        Long savedMeetingId = meetingService.create(userDetails, form);

        flushAndClear();
        Meeting meeting = meetingRepository.findById(savedMeetingId)
                .orElseThrow(EntityNotFoundException::new);
        assertThat(meeting.getTitle()).isEqualTo(title);
        assertThat(meeting.getStartTime()).isEqualTo(startTime);
        assertThat(meeting.getEndTime()).isEqualTo(endTime);
        assertThat(meeting.getMeetingRoom().getId()).isEqualTo(form.getMeetingRoomId());
        assertThat(meeting.getUser().getUsername()).isEqualTo(userDetails.getUsername());
    }

    private MeetingRoom getMeetingRoom() {
        return MeetingRoom.builder()
               .name("meetingRoom1")
               .build();
    }

    @DisplayName("같은 시간과 회의가 이미 있으면 예약 생성에 실패한다.")
    @Test
    public void createMeetingFail() {
        LocalDateTime startTime = LocalDateTime.of(2024, 6, 6, 12, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 6, 6, 15, 0, 0);

        Team team = teamRepository.save(getTeam());
        User user = userRepository.save(getUser(team));
        MeetingRoom meetingRoom = meetingRoomRepository.save(getMeetingRoom());
        CustomUserDetails userDetails = CustomUserDetails.from(user);
        CreateMeetingForm form = getCreateMeetingForm(meetingRoom.getId(), startTime, endTime, "title1");
        flushAndClear();

        Long savedMeetingId = meetingService.create(userDetails, form);

    }

    private void flushAndClear() {
        em.flush();
        em.clear();
    }

    private CreateMeetingForm getCreateMeetingForm(Long meetingRoomId, LocalDateTime startTime, LocalDateTime endTime, String title) {
        return CreateMeetingForm.builder()
                .startTime(startTime)
                .endTime(endTime)
                .meetingRoomId(meetingRoomId)
                .title(title)
                .build();
    }

    private User getUser(Team team) {
        return User.builder()
                .username("username1")
                .team(team)
                .password("password1")
                .name("name1")
                .roleType(RoleType.USER)
                .build();
    }

    private Team getTeam() {
        return Team.builder()
                .name("team1")
                .build();
    }
}
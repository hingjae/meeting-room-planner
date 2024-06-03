package com.honey.meetingroomplanner.meeting.service;

import com.honey.meetingroomplanner.meeting.controller.dto.CreateMeetingForm;
import com.honey.meetingroomplanner.meeting.entity.Meeting;
import com.honey.meetingroomplanner.meeting.exception.MeetingRoomAlreadyBookedException;
import com.honey.meetingroomplanner.meeting.repository.MeetingRepository;
import com.honey.meetingroomplanner.mettingroom.entity.MeetingRoom;
import com.honey.meetingroomplanner.mettingroom.repository.MeetingRoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MeetingServiceTest {

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

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
}
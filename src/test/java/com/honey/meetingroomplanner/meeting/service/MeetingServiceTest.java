package com.honey.meetingroomplanner.meeting.service;

import com.honey.meetingroomplanner.meeting.dto.CreateMeetingForm;
import com.honey.meetingroomplanner.meeting.entity.Meeting;
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

@SpringBootTest
class MeetingServiceTest {

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    public static final String MEETING_ROOM_NAME = "meetingRoom1";

    @BeforeEach
    public void beforeEach() {
        MeetingRoom meetingRoom = MeetingRoom.builder()
                .name(MEETING_ROOM_NAME)
                .build();

        meetingRoomRepository.save(meetingRoom);
    }

    @Test
    public void 예약을_생성한다() {
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
}
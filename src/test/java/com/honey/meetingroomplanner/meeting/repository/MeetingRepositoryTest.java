package com.honey.meetingroomplanner.meeting.repository;

import com.honey.meetingroomplanner.meeting.entity.Meeting;
import com.honey.meetingroomplanner.mettingroom.entity.MeetingRoom;
import com.honey.meetingroomplanner.mettingroom.repository.MeetingRoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MeetingRepositoryTest {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    private MeetingRoom getMeetingRoom() {
        return MeetingRoom.builder()
                .name("meetingRoomA")
                .build();
    }

    private Meeting getMeeting(MeetingRoom meetingRoom, String title, LocalDateTime startTime, LocalDateTime endTime) {
        return Meeting.builder()
                .meetingRoom(meetingRoom)
                .title(title)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

    @Test
    public void 곂치는_회의를조회() {
        MeetingRoom savedMeetingRoom = meetingRoomRepository.save(getMeetingRoom());
        Meeting meeting1 = getMeeting(savedMeetingRoom, "meeting1", LocalDateTime.of(2024, 6, 4, 2, 0, 0), LocalDateTime.of(2024, 6, 4, 4, 0, 0));
        Meeting meeting2 = getMeeting(savedMeetingRoom, "meeting2", LocalDateTime.of(2024, 6, 4, 5, 0, 0), LocalDateTime.of(2024, 6, 4, 6, 0, 0));
        Meeting meeting3 = getMeeting(savedMeetingRoom, "meeting3", LocalDateTime.of(2024, 6, 4, 8, 0, 0), LocalDateTime.of(2024, 6, 4, 12, 0, 0));
        Meeting meeting4 = getMeeting(savedMeetingRoom, "meeting4", LocalDateTime.of(2024, 6, 4, 13, 0, 0), LocalDateTime.of(2024, 6, 4, 15, 0, 0));

        meetingRepository.save(meeting1);
        meetingRepository.save(meeting2);
        meetingRepository.save(meeting3);
        meetingRepository.save(meeting4);

        LocalDateTime startTime = LocalDateTime.of(2024, 6, 4, 6, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 6, 4, 8, 0, 0);

        List<Meeting> result = meetingRepository.findOverlappingMeetings(savedMeetingRoom.getId(), startTime, endTime);

        assertThat(result).isEmpty();
    }

    @Test
    public void 회의가_곂치는지_조회한다() {
        MeetingRoom savedMeetingRoom = meetingRoomRepository.save(getMeetingRoom());
        Meeting meeting1 = getMeeting(savedMeetingRoom, "meeting1", LocalDateTime.of(2024, 6, 4, 2, 0, 0), LocalDateTime.of(2024, 6, 4, 4, 0, 0));
        Meeting meeting2 = getMeeting(savedMeetingRoom, "meeting2", LocalDateTime.of(2024, 6, 4, 5, 0, 0), LocalDateTime.of(2024, 6, 4, 6, 0, 0));
        Meeting meeting3 = getMeeting(savedMeetingRoom, "meeting3", LocalDateTime.of(2024, 6, 4, 8, 0, 0), LocalDateTime.of(2024, 6, 4, 12, 0, 0));
        Meeting meeting4 = getMeeting(savedMeetingRoom, "meeting4", LocalDateTime.of(2024, 6, 4, 13, 0, 0), LocalDateTime.of(2024, 6, 4, 15, 0, 0));

        meetingRepository.save(meeting1);
        meetingRepository.save(meeting2);
        meetingRepository.save(meeting3);
        meetingRepository.save(meeting4);

        LocalDateTime startTime = LocalDateTime.of(2024, 6, 4, 1, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 6, 4, 4, 0, 0);

        List<Meeting> result = meetingRepository.findOverlappingMeetings(savedMeetingRoom.getId(), startTime, endTime);

        assertThat(result).isNotEmpty();
    }
}
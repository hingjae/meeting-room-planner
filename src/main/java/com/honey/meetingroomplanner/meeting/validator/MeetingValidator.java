package com.honey.meetingroomplanner.meeting.validator;

import com.honey.meetingroomplanner.meeting.entity.Meeting;
import com.honey.meetingroomplanner.meeting.exception.MeetingRoomAlreadyBookedException;
import com.honey.meetingroomplanner.meeting.repository.MeetingRepository;
import com.honey.meetingroomplanner.mettingroom.entity.MeetingRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class MeetingValidator {
    private final MeetingRepository meetingRepository;

    public void validateMeetingTime(MeetingRoom meetingRoom, LocalDateTime startTime, LocalDateTime endTime) {
        List<Meeting> overlappingMeetings = meetingRepository.findOverlappingMeetings(meetingRoom.getId(), startTime, endTime);
        if (!overlappingMeetings.isEmpty()) {
            throw new MeetingRoomAlreadyBookedException();
        }
    }
}

package com.honey.meetingroomplanner.meeting.service;

import com.honey.meetingroomplanner.meeting.controller.dto.CreateMeetingForm;
import com.honey.meetingroomplanner.meeting.entity.Meeting;
import com.honey.meetingroomplanner.meeting.repository.MeetingRepository;
import com.honey.meetingroomplanner.mettingroom.entity.MeetingRoom;
import com.honey.meetingroomplanner.mettingroom.repository.MeetingRoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;

    private final MeetingRoomRepository meetingRoomRepository;

    @Transactional
    public Long save(CreateMeetingForm form) {
        // Todo : 캐시 추가, 회의실 중복 검사
        MeetingRoom meetingRoom = meetingRoomRepository.findById(form.getMeetingRoomId())
                .orElseThrow(EntityNotFoundException::new);

        Meeting meeting = Meeting.create(meetingRoom, form.getTitle(), form.getStartTime(), form.getEndTime());

        return meetingRepository.save(meeting).getId();
    }
}

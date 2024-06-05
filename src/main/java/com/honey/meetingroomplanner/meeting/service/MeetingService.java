package com.honey.meetingroomplanner.meeting.service;

import com.honey.meetingroomplanner.meeting.controller.dto.CreateMeetingForm;
import com.honey.meetingroomplanner.meeting.entity.Meeting;
import com.honey.meetingroomplanner.meeting.repository.MeetingRepository;
import com.honey.meetingroomplanner.meeting.validator.MeetingValidator;
import com.honey.meetingroomplanner.mettingroom.entity.MeetingRoom;
import com.honey.meetingroomplanner.mettingroom.repository.MeetingRoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;

    private final MeetingRoomRepository meetingRoomRepository;

    private final MeetingValidator meetingValidator;

    @Transactional
    public Long save(CreateMeetingForm form) {
        // Todo : 캐시 추가
        MeetingRoom meetingRoom = meetingRoomRepository.findById(form.getMeetingRoomId())
                .orElseThrow(EntityNotFoundException::new);
        LocalDateTime startTime = form.getStartTime();
        LocalDateTime endTime = form.getEndTime();

        meetingValidator.validateMeetingTime(meetingRoom, startTime, endTime);

        Meeting meeting = Meeting.create(meetingRoom, form.getTitle(), startTime, endTime);

        return meetingRepository.save(meeting).getId();
    }

    public List<Meeting> findByDate(LocalDate dateParam) {
        return meetingRepository.findAllByDate(dateParam);
    }
}

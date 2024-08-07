package com.honey.meetingroomplanner.meeting.service;

import com.honey.meetingroomplanner.meeting.controller.dto.CreateMeetingForm;
import com.honey.meetingroomplanner.meeting.entity.Meeting;
import com.honey.meetingroomplanner.meeting.repository.MeetingRepository;
import com.honey.meetingroomplanner.meeting.validator.MeetingValidator;
import com.honey.meetingroomplanner.mettingroom.entity.MeetingRoom;
import com.honey.meetingroomplanner.mettingroom.repository.MeetingRoomRepository;
import com.honey.meetingroomplanner.security.dto.CustomUserDetails;
import com.honey.meetingroomplanner.user.entity.User;
import com.honey.meetingroomplanner.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;

    private final MeetingRoomRepository meetingRoomRepository;

    private final MeetingValidator meetingValidator;
    private final UserRepository userRepository;

    @Transactional
    public Long save(CreateMeetingForm form) {
        // Todo : 캐시 추가
        MeetingRoom meetingRoom = meetingRoomRepository.findById(form.getMeetingRoomId())
                .orElseThrow(EntityNotFoundException::new);
        LocalDateTime startTime = form.getStartTime();
        LocalDateTime endTime = form.getEndTime();

        meetingValidator.validateMeetingTime(meetingRoom, startTime, endTime);

        Meeting meeting = Meeting.create(meetingRoom, null, form.getTitle(), startTime, endTime);

        return meetingRepository.save(meeting).getId();
    }

    public List<Meeting> findByDate(LocalDate date) {
        return meetingRepository.findAllByDate(date);
    }

    @Transactional
    public Long create(CustomUserDetails userDetails, CreateMeetingForm form) {
        User user = userRepository.findById(userDetails.getUsername())
                .orElseThrow(EntityNotFoundException::new);
        MeetingRoom meetingRoom = meetingRoomRepository.findById(form.getMeetingRoomId())
                .orElseThrow(EntityNotFoundException::new);
        LocalDateTime startTime = form.getStartTime();
        LocalDateTime endTime = form.getEndTime();

        meetingValidator.validateMeetingTime(meetingRoom, startTime, endTime);

        Meeting meeting = Meeting.create(meetingRoom, user, form.getTitle(), startTime, endTime);

        return meetingRepository.save(meeting).getId();
    }
}

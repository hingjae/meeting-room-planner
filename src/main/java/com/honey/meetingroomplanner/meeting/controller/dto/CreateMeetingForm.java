package com.honey.meetingroomplanner.meeting.controller.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class CreateMeetingForm {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final LocalDateTime startTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final LocalDateTime endTime;

    private final Long meetingRoomId;

    private final String title;

    @Builder
    public CreateMeetingForm(LocalDateTime startTime, LocalDateTime endTime, Long meetingRoomId, String title) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.meetingRoomId = meetingRoomId;
        this.title = title;
    }
}

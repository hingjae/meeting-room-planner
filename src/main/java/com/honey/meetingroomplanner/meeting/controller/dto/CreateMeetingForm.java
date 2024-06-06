package com.honey.meetingroomplanner.meeting.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class CreateMeetingForm {
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final LocalDateTime startTime;

    @NotNull
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

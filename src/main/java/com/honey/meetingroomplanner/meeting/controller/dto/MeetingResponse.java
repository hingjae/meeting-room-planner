package com.honey.meetingroomplanner.meeting.controller.dto;

import com.honey.meetingroomplanner.meeting.entity.Meeting;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MeetingResponse {
    private final Long meetingId;

    private final String title;

    private final LocalDateTime startTime;

    private final LocalDateTime endTime;

    private final Long meetingRoomId;

    private final String meetingRoomName;

    @Builder
    public MeetingResponse(Long meetingId, String title, LocalDateTime startTime, LocalDateTime endTime, Long meetingRoomId, String meetingRoomName) {
        this.meetingId = meetingId;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.meetingRoomId = meetingRoomId;
        this.meetingRoomName = meetingRoomName;
    }

    public static MeetingResponse from(Meeting entity) {
        return MeetingResponse.builder()
                .meetingId(entity.getId())
                .title(entity.getTitle())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .meetingRoomId(entity.getMeetingRoom().getId())
                .meetingRoomName(entity.getMeetingRoom().getName())
                .build();
    }
}

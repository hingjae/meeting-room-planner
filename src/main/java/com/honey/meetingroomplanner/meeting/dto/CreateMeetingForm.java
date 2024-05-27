package com.honey.meetingroomplanner.meeting.dto;

import com.honey.meetingroomplanner.meeting.entity.Meeting;
import com.honey.meetingroomplanner.mettingroom.entity.MeetingRoom;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class CreateMeetingForm {
    //Todo: startTime, endTime 00분, 30분단위로 끊어지는 검증 로직 필요함.
    private final LocalDateTime startTime;

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

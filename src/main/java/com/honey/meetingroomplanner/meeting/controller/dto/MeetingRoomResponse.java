package com.honey.meetingroomplanner.meeting.controller.dto;

import com.honey.meetingroomplanner.mettingroom.entity.MeetingRoom;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MeetingRoomResponse {
    private final Long id;

    private final String name;

    @Builder
    public MeetingRoomResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static MeetingRoomResponse from(MeetingRoom meetingRoom) {
        return MeetingRoomResponse.builder()
                .id(meetingRoom.getId())
                .name(meetingRoom.getName())
                .build();
    }
}

package com.honey.meetingroomplanner.meeting.controller.dto;

import com.honey.meetingroomplanner.mettingroom.entity.MeetingRoom;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MeetingRoomList {
    private final List<MeetingRoomResponse> items;

    @Builder
    public MeetingRoomList(List<MeetingRoomResponse> items) {
        this.items = items;
    }

    public static MeetingRoomList from(List<MeetingRoom> meetingRooms) {
        return MeetingRoomList.builder()
                .items(
                        meetingRooms.stream()
                                .map(MeetingRoomResponse::from)
                                .collect(Collectors.toList())
                )
                .build();
    }
}

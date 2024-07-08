package com.honey.meetingroomplanner.meeting.controller.dto;

import com.honey.meetingroomplanner.meeting.entity.Meeting;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MeetingResponseList {
    private final List<MeetingResponse> items;

    @Builder
    public MeetingResponseList(List<MeetingResponse> items) {
        this.items = items;
    }

    public static MeetingResponseList from(List<Meeting> meetings) {
        return MeetingResponseList.builder()
                .items(
                        meetings.stream()
                        .map(MeetingResponse::from)
                        .collect(Collectors.toList())
                )
                .build();
    }
}

package com.honey.meetingroomplanner.meeting.entity;

import com.honey.meetingroomplanner.common.BaseEntity;
import com.honey.meetingroomplanner.mettingroom.entity.MeetingRoom;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeting extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_room_id")
    private MeetingRoom meetingRoom;

    private String title;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Builder
    public Meeting(Long id, MeetingRoom meetingRoom, String title, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.meetingRoom = meetingRoom;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public static Meeting create(MeetingRoom meetingRoom, String title, LocalDateTime startTime, LocalDateTime endTime) {
        return Meeting.builder()
                .meetingRoom(meetingRoom)
                .title(title)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}

package com.honey.meetingroomplanner.mettingroom.service;

import com.honey.meetingroomplanner.mettingroom.entity.MeetingRoom;
import com.honey.meetingroomplanner.mettingroom.repository.MeetingRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MeetingRoomService {
    private final MeetingRoomRepository meetingRoomRepository;

    public List<MeetingRoom> findAll() {
        return meetingRoomRepository.findAll();
    }
}

package com.honey.meetingroomplanner.mettingroom.repository;

import com.honey.meetingroomplanner.mettingroom.entity.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long> {
    MeetingRoom findByName(String name);
}

package com.honey.meetingroomplanner.meeting.repository;

import com.honey.meetingroomplanner.meeting.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}

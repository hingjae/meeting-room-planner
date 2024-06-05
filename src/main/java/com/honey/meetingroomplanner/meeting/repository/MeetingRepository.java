package com.honey.meetingroomplanner.meeting.repository;

import com.honey.meetingroomplanner.meeting.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    @Query("select m from Meeting m where m.meetingRoom.id = :meetingRoomId and" +
            " (m.endTime > :startTime and m.startTime < :endTime)")
    List<Meeting> findOverlappingMeetings(@Param("meetingRoomId") Long meetingRoomId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("select m from Meeting m where DATE(m.startTime) = :date")
    List<Meeting> findAllByDate(@Param("date") LocalDate dateParam);
}

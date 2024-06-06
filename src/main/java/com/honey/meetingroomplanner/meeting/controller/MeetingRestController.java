package com.honey.meetingroomplanner.meeting.controller;

import com.honey.meetingroomplanner.meeting.controller.dto.MeetingResponseList;
import com.honey.meetingroomplanner.meeting.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RequestMapping("/api/meetings")
@RequiredArgsConstructor
@RestController
public class MeetingRestController {

    private final MeetingService meetingService;

    @GetMapping
    public MeetingResponseList getMeetingByDate(@RequestParam("date") LocalDate date) {
        return MeetingResponseList.from(meetingService.findByDate(date));
    }
}

package com.honey.meetingroomplanner.meeting.controller;

import com.honey.meetingroomplanner.meeting.controller.dto.MeetingRoomList;
import com.honey.meetingroomplanner.meeting.dto.CreateMeetingForm;
import com.honey.meetingroomplanner.meeting.service.MeetingService;
import com.honey.meetingroomplanner.mettingroom.service.MeetingRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class MeetingController {

    private final MeetingService meetingService;

    private final MeetingRoomService meetingRoomService;

    @GetMapping("/meeting/new")
    public String createMeetingForm(@ModelAttribute("meeting") CreateMeetingForm form, Model model) {
        model.addAttribute("teams", MeetingRoomList.from(meetingRoomService.findAll()).getItems());
        return "meeting/create-meeting-form";
    }

    @PostMapping("/meeting/new")
    public String create(@ModelAttribute("meeting") CreateMeetingForm form) {
        meetingService.save(form);
        return "redirect:/";
    }
}

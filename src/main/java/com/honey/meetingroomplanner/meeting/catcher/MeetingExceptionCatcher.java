package com.honey.meetingroomplanner.meeting.catcher;

import com.honey.meetingroomplanner.meeting.controller.MeetingController;
import com.honey.meetingroomplanner.meeting.exception.MeetingRoomAlreadyBookedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice(assignableTypes = MeetingController.class)
public class MeetingExceptionCatcher {

    @ExceptionHandler(MeetingRoomAlreadyBookedException.class)
    public String handleMeetingRoomAlreadyBookedException(MeetingRoomAlreadyBookedException ex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        redirectAttributes.addAttribute("date", request.getParameter("date"));
        return "redirect:/meeting/new";
    }
}

package com.honey.meetingroomplanner.user.controller;

import com.honey.meetingroomplanner.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;

    @GetMapping("/sign-up")
    public String signUp(@ModelAttribute("user") CreateUserForm form) {
        return "user/sign-up";
    }
}

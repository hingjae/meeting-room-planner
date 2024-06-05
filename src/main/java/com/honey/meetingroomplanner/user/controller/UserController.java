package com.honey.meetingroomplanner.user.controller;

import com.honey.meetingroomplanner.team.service.TeamService;
import com.honey.meetingroomplanner.user.exception.UsernameAlreadyExistsException;
import com.honey.meetingroomplanner.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    private final TeamService teamService;

    @GetMapping("/users/new")
    public String signUpView(@ModelAttribute("user") CreateUserForm form, Model model) {
        model.addAttribute("teams", TeamResponseList.from(teamService.findAll()).getItems());
        return "user/sign-up";
    }

    @PostMapping("/users/new")
    public String signUp(@Valid @ModelAttribute("user") CreateUserForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("teams", TeamResponseList.from(teamService.findAll()).getItems());
            return "user/sign-up";
        }

        try {
            userService.create(form);
            return "redirect:/";
        } catch (UsernameAlreadyExistsException e) {
            bindingResult.reject("UsernameAlreadyExists");
            model.addAttribute("teams", TeamResponseList.from(teamService.findAll()).getItems());
            return "user/sign-up";
        }
    }
}

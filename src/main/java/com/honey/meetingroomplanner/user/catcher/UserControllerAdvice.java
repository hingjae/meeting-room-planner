package com.honey.meetingroomplanner.user.catcher;

import com.honey.meetingroomplanner.user.exception.UsernameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@ControllerAdvice(basePackages = "com.honey.meetingroomplanner.user.controller")
public class UserControllerAdvice {
    private final MessageSource messageSource;

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public String usernameAlreadyExistsException(Exception ex, Model model) {
        model.addAttribute("errorMessage", messageSource.getMessage("UsernameAlreadyExists.username.user", null, null));
        return "user/sign-up";
    }
}

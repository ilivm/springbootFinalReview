package com.udacity.jwdnd.c1.review.controller;

import com.udacity.jwdnd.c1.review.service.AuthenticationServiceGpt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    private AuthenticationServiceGpt authenticationServiceGpt;

    public LoginController(AuthenticationServiceGpt authenticationServiceGpt) {
        this.authenticationServiceGpt = authenticationServiceGpt;
    }

    @GetMapping()
    public String loginView() {
        return "loginGpt";
    }

    @PostMapping()
    public String loginSubmit(String username, String password, Model model) {
        boolean authenticated = authenticationServiceGpt.authenticate(username, password);
//        String loginError = null;

        if (authenticated) {
            return "chat";
        } else {
//            loginError = "login failed";
            model.addAttribute("error", "login failed");
            return "loginGpt";
        }

    }
}

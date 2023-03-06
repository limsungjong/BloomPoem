package com.example.bloompoem.controller;

import com.example.bloompoem.domain.dto.CustomUserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/sign")
public class SignController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping("/sign_in")
    public String loginForm(Model model, @AuthenticationPrincipal CustomUserDetail customUserDetail) {

        return "/signIn";
    }
    @GetMapping("/sign_up")
    public String signUpForm() {
        return "/signUp";
    }

    @GetMapping("/loginSuccess")
    public String success() {
        return "/loginSuccess";
    }
}

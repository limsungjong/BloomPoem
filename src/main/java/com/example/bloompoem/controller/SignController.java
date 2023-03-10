package com.example.bloompoem.controller;

import com.example.bloompoem.domain.dto.CustomUserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/sign")
public class SignController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping("/sign_in")
    public String loginForm(@AuthenticationPrincipal CustomUserDetail customUserDetail) {

        return "/signIn";
    }
    @GetMapping("/sign_up")
    public String signUpForm() {
        return "/signUp";
    }

    @GetMapping("/sing_out")
    public String singOut(HttpServletResponse res) {

        Cookie cookie = new Cookie("Authorization", null);
        res.addCookie(cookie);
        return "/signIn";
    }
}

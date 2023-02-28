package com.example.bloompoem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/sign")
public class SignController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping("/sign_in")
    public String loginForm(Model model) {
        return "/signIn";
    }
    @GetMapping("/sign_up")
    public String signUpForm() {
        return "/signUp";
    }
}

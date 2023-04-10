package com.example.bloompoem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "/index";
    }

    @PostMapping("/")
    public String postIndex() {
        return "/index";
    }

    @GetMapping(value = "/privacy")
    public String privacy() {
        return "/privacy";
    }
}

package com.example.bloompoem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "/index";
    }

    @GetMapping(value = "/privacy")
    public String privacy() {
        return "/privacy";
    }
}

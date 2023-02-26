package com.example.bloompoem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller(value = "/user")
public class UserController {
    @GetMapping
    public String user() {
        return "user";
    }
}

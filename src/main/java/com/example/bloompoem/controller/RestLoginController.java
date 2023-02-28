package com.example.bloompoem.controller;

import com.example.bloompoem.dto.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController(value = "/api/login")
public class RestLoginController {
    @PostMapping("/login")
    public UserDTO login() {
        UserDTO data = new UserDTO();

        return data;
    }
}

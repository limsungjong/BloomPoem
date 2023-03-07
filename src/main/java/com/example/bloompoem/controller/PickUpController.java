package com.example.bloompoem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PickUpController {
    @GetMapping(value = "/pick_up")
    public String pickUpPage() {
        return "/pickUp";
    }

    @PostMapping(value = "/pick_up")
    public ResponseEntity<String> pick() {
        return ResponseEntity.ok().body("성공");
    }
}

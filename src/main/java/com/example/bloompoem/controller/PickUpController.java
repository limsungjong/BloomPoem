package com.example.bloompoem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@CrossOrigin("http://172.28.16.1:5500")
public class PickUpController {
    @GetMapping(value = "/pick_up")
    public String pickUpPage() {
        return "/pickUp";
    }
}

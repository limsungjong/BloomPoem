package com.example.bloompoem.controller;

import com.example.bloompoem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class PickUpController {
    private final UserService userService;
    @GetMapping(value = "/pick_up")
    public String pickUpPage(Principal principal, Authentication authentication) {
        System.out.println(principal.getName());
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(userDetails.getUsername());
        return "/pickUp";
    }
}

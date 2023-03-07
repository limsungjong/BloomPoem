package com.example.bloompoem.controller;

import com.example.bloompoem.domain.dto.CustomUserDetail;
import com.example.bloompoem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PickUpController {
    private final UserService userService;
    @GetMapping(value = "/pick_up")
    public String pickUpPage(@AuthenticationPrincipal CustomUserDetail customUserDetail) {
        System.out.println(customUserDetail.getTestUserEntity().getUserEmail());
        return "/pickUp";
    }
}

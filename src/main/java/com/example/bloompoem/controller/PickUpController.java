package com.example.bloompoem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@CrossOrigin("http://172.28.16.1:5500")
public class PickUpController {
    @GetMapping(value = "/pick_up")
    public String pickUpPage() {
        return "/pickUp";
    }

    @PostMapping(value = "/pick_up")
    public String postPickUpPage(
            @RequestParam String flowerName,
            Model model
    ) {
        model.addAttribute("flowerName", flowerName);
        return "/pickUp";
    }

    @PostMapping(value = "/pick_up/order/pay/cancel")
    public String orderCancel() {
        return "payFail";
    }

    @PostMapping(value = "/pick_up/order/pay/fail")
    public String orderFail() {
        return "payFail";
    }
}

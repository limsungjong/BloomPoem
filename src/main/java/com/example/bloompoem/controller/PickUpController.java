package com.example.bloompoem.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

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
            @RequestParam String kind,
            @RequestParam String target,
            Model model
    ) {
        model.addAttribute("kind",kind);
        model.addAttribute("target",target);
        return "/pickUp";
    }

    @PostMapping (value = "/pick_up/order/pay/cancel")
    public String orderCancel() {
        return "payFail";
    }
    @PostMapping (value = "/pick_up/order/pay/fail")
    public String orderFail() {
        return "payFail";
    }
}

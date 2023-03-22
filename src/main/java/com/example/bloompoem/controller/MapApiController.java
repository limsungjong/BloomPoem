package com.example.bloompoem.controller;

import com.example.bloompoem.service.KakaoMapApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@PropertySource("classpath:app.properties")
public class MapApiController {


    private final KakaoMapApiService kakaoMapApiService;

    @PostMapping("/searchPlace/pickUp")
    public String searchPlace(@RequestParam String query, Model model) {
        System.out.println(query);
        try {
            model.addAttribute("search", kakaoMapApiService.outRestKakaoPlace(query).get(0));
            return "pickUp";

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

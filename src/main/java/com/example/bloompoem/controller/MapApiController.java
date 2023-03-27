package com.example.bloompoem.controller;

import com.example.bloompoem.domain.dto.ResponseCode;
import com.example.bloompoem.domain.dto.RestKakaoPlaceResponse;
import com.example.bloompoem.exception.CustomException;
import com.example.bloompoem.repository.FloristRepository;
import com.example.bloompoem.service.KakaoMapApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@PropertySource("classpath:app.properties")
public class MapApiController {

    private final KakaoMapApiService kakaoMapApiService;

    private final FloristRepository floristRepository;

    @PostMapping("/searchPlace/pickUp")
    public String searchPlace(@RequestParam String query, Model model) {
        List<RestKakaoPlaceResponse> list = new ArrayList<>();
        try {
            if(kakaoMapApiService.outRestKakaoPlace(query).size() > 0) {
                model.addAttribute("search", kakaoMapApiService.outRestKakaoPlace(query).get(0));
                return "pickUp";
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return "pickUp";
    }

    @PostMapping("/searchFloristName")
    public String searchFloristName(@RequestParam String floristName, Model model) {
        if(floristRepository.existsByFloristName(floristName)) {
            throw new CustomException(ResponseCode.INVALID_REQUEST);
        }
        try {
            model.addAttribute("floristEntity", floristRepository.findByFloristName(floristName));
            return "pickUp";

        } catch (RuntimeException e) {
            throw new CustomException(ResponseCode.INVALID_REQUEST);
        }
    }
}

package com.example.bloompoem.controller;

import com.example.bloompoem.domain.UserSignUpRequest;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://192.168.1.135:5500/")
@RestController
@RequestMapping(value = "/api/v1/sign")
public class RestSignController {

    @PostMapping("/signUp")
    public String singUp(@RequestBody UserSignUpRequest dto) {
        System.out.println(dto.getUserEmail());
        System.out.println(dto.getUserName());
        System.out.println(dto.getUserAddress());
        System.out.println(dto.getUserAddressDetail());
        System.out.println(dto.getUserPhoneNumber());

        return "여기는 회원 가입";
    }
    @PostMapping("/signIn")
    public String singIn(@RequestBody UserSignUpRequest dto) {
        System.out.println(dto);
        return "여기는 로그인";
    }
}

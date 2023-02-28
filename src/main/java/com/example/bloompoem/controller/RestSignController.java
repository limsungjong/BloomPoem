package com.example.bloompoem.controller;

import com.example.bloompoem.domain.UserSignUpRequest;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://192.168.1.135:5500/")
@RestController
@RequestMapping(value = "/api/v1/sign")
public class RestSignController {

//     들어오는 데이터 형식
//     "userEmail":"sung8881@naver.com",
//     "userAddress":"인천 중구 제물량로 121-1",
//     "userAddressDetail":"아르누보 506호",
//     "userPhoneNumber":"010-3716-8640",
//     "userName":"임성종"

    @PostMapping("/sign_up")
    public String singUp(@RequestBody UserSignUpRequest dto) {
        System.out.println(dto.getUserEmail());
        System.out.println(dto.getUserName());
        System.out.println(dto.getUserAddress());
        System.out.println(dto.getUserAddressDetail());
        System.out.println(dto.getUserPhoneNumber());

        return "여기는 회원 가입";
    }
    @PostMapping("/sign_in")
    public String singIn(@RequestBody UserSignUpRequest dto) {
        System.out.println(dto);
        return "여기는 로그인";
    }
}

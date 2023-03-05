package com.example.bloompoem.controller;

import com.example.bloompoem.domain.dto.*;
import com.example.bloompoem.service.SignService;
import com.example.bloompoem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://192.168.45.124:5500/")
@RestController
@RequestMapping(value = "/api/v1/sign")
@RequiredArgsConstructor
public class RestSignController {

    private final UserService userService;

//     들어오는 데이터 형식 // json
//     "userEmail":"sung8881@naver.com",
//     "userAddress":"인천 중구 제물량로 121-1",
//     "userAddressDetail":"아르누보 506호",
//     "userPhoneNumber":"010-3716-8640",
//     "userName":"임성종"

    @PostMapping("/sign_up")
    public ResponseEntity<UserSignResponse> singUp(@RequestBody UserSignUpRequest request) {
        userService.createUser(request);
        return UserSignResponse.toResponseEntity(ResponseCode.CREATE);
    }


    @PostMapping("/otp_check")
    public ResponseEntity<UserSignResponse> signUpOtpCheck(@RequestBody UserSignInRequest request) {
        System.out.println(request.getUserOtp());
        System.out.println(request.getUserEmail());

        return UserSignResponse.toResponseEntity(ResponseCode.SUCCESSFUL);
    }

    @PostMapping("/sign_in")
    public String singIn(@RequestBody UserSignUpRequest dto) {
        System.out.println(dto);
        return "여기는 로그인";
    }
}

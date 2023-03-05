package com.example.bloompoem.controller;

import com.example.bloompoem.domain.dto.*;
import com.example.bloompoem.service.SignService;
import com.example.bloompoem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://192.168.1.135:5500/")
@RestController
@RequestMapping(value = "/api/v1/sign")
@RequiredArgsConstructor
public class RestSignController {

    private final UserService userService;
    private final SignService signService;

//     들어오는 데이터 형식 // json
//     "userEmail":"sung8881@naver.com",
//     "userAddress":"인천 중구 제물량로 121-1",
//     "userAddressDetail":"아르누보 506호",
//     "userPhoneNumber":"010-3716-8640",
//     "userName":"임성종"

    @PostMapping("/sign_up")
    public ResponseEntity<UserSignResponse> singUp(@RequestBody @Valid UserSignUpRequest request) {

        UserSignResponse res = new UserSignResponse();
        if (request.getUserEmail().isEmpty() || request.getUserEmail().isBlank()) {
            System.out.println("addressEmpty");
            res.setStatus(-400);
            res.setReason("주소 입력 형식이 맞지 않습니다.");
        }
        if (request.getUserName().isEmpty() || request.getUserName().isBlank()) {
            System.out.println("nameEmpty");
            res.setStatus(-400);
            res.setReason("이름 입력 형식이 맞지 않습니다.");
        }
        if (request.getUserPhoneNumber().isEmpty() || request.getUserPhoneNumber().isBlank()) {
            System.out.println("phoneNumEmpty");
            res.setStatus(-400);
            res.setReason("연락처 입력 형식이 맞지 않습니다.");
        }
        if (request.getUserEmail().isEmpty() || request.getUserEmail().isBlank()) {
            System.out.println("userEmailEmpty");
            res.setStatus(-400);
            res.setReason("이메일 입력 형식이 맞지 않습니다.");
        }

        int result = userService.createUser(request);
        if (result == 0) {
            userService.userOtpMailSend(request);
            res.setStatus(0);
            res.setReason("Otp메일 전송");
        } else if (result == -1) {
            res.setStatus(-1);
            res.setReason("이메일이 중복됩니다.");
        } else if (result == -10) {
            res.setStatus(-10);
            res.setReason("이메일이 형식에 맞지 않습니다.");
        } else {
            res.setStatus(400);
            res.setReason("잘못된 요청입니다.");
        }
        return ResponseEntity.ok().body(res);
    }


    @PostMapping("/otp_check")
    public ResponseEntity<UserSignResponse> signUpOtpCheck(@RequestBody UserSignInRequest request) {
        String token = signService.login(request);
        UserSignResponse res = new UserSignResponse();
        System.out.println(request.getUserOtp());
        System.out.println(request.getUserEmail());
        System.out.println(token);
        if (token.isEmpty()) {
            System.out.println("is empty"+token);
            res.setStatus(-1);
            res.setReason("retry");
        } else {
            res.setStatus(0);
            res.setReason("success");
        }
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/sign_in")
    public String singIn(@RequestBody UserSignUpRequest dto) {
        System.out.println(dto);
        return "여기는 로그인";
    }
}

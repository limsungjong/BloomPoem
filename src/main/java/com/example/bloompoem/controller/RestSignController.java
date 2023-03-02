package com.example.bloompoem.controller;

import com.example.bloompoem.domain.dto.MailCheckRequest;
import com.example.bloompoem.domain.dto.UserSignUpRequest;
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
    public ResponseEntity<String> singUp(@RequestBody @Valid UserSignUpRequest dto) {
        int result = signService.userCheck(dto);

        if (result == 0) {
            signService.userCheckMailSend(dto);
            return ResponseEntity.ok().body("success");
        } else if (result == -1) {
            return ResponseEntity.ok().body("이메일이 중복 됩니다.");
        } else if (result == -10) {
            return ResponseEntity.ok().body("입력한 이메일 형식이 잘못된 요청입니다.");
        } else {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }
    }


    @PostMapping("/sign_up_otp_check")
    public String signUpOtpCheck(@RequestBody MailCheckRequest dto) {
        System.out.println(dto.getUserEmail());
        System.out.println(dto.getOTP());

        return "여기는 이메일 체크";
    }

    @PostMapping("/sign_in")
    public String singIn(@RequestBody UserSignUpRequest dto) {
        System.out.println(dto);
        return "여기는 로그인";
    }
}

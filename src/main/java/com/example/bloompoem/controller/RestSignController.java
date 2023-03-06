package com.example.bloompoem.controller;

import com.example.bloompoem.domain.dto.*;
import com.example.bloompoem.entity.TestUserEntity;
import com.example.bloompoem.exception.CustomException;
import com.example.bloompoem.repository.TestUserRepository;
import com.example.bloompoem.service.SignService;
import com.example.bloompoem.service.UserService;
import com.example.bloompoem.util.JwtUtil;
import com.example.bloompoem.util.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://192.168.45.124:5500/")
@RestController
@RequestMapping(value = "/api/v1/sign")
@RequiredArgsConstructor
public class RestSignController {

    private final UserService userService;
    private final TestUserRepository testUserRepository;
    private final SignService signService;

    private Long expiredMs = 1000 * 60 * 60L;

    @Value("#{environment['jwt.secret']}")
    private String secretKey;

    private final PasswordEncoder passwordEncoder;

//     들어오는 데이터 형식 // json
//     "userEmail":"sung8881@naver.com",
//     "userAddress":"인천 중구 제물량로 121-1",
//     "userAddressDetail":"아르누보 506호",
//     "userPhoneNumber":"010-3716-8640",
//     "userName":"임성종"

    @PostMapping("/sign_up")
    public ResponseEntity<UserSignResponse> singUp(@RequestBody UserSignUpRequest request) {
        userService.createUser(request);
        return UserSignResponse.toResponseEntity(ResponseCode.CREATE, null);
    }


    @PostMapping("/otp_check")
    public ResponseEntity<UserSignResponse> signUpOtpCheck(@RequestBody UserSignInRequest request) {
        TestUserEntity testUserEntity = testUserRepository
                .findByUserEmail(request.getUserEmail())
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_NOT_FOUND));

        String userEmail = testUserEntity.getUserEmail();
        String userName = testUserEntity.getUserName();
        String otp = OtpUtil.createOTP(6);
        userService.userOtpMailSend(userEmail, userName, otp);
        userService.userUpdateOTP(userEmail, otp);

        return UserSignResponse.toResponseEntity(ResponseCode.SEND_OTP_MAIL, null);
    }

    @PostMapping("/sign_in")
    public ResponseEntity<UserSignResponse> singIn(@RequestBody UserSignInRequest request) {
        System.out.println(request.getUserOtp());
        System.out.println(request.getUserEmail());

        TestUserEntity testUserEntity = testUserRepository
                .findByUserEmail(request.getUserEmail())
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_NOT_FOUND));
        return UserSignResponse.toResponseEntity(
                ResponseCode.SUCCESSFUL, signService.login(request));
    }
}

package com.example.bloompoem.controller;

import com.example.bloompoem.domain.dto.*;
import com.example.bloompoem.entity.UserEntity;
import com.example.bloompoem.exception.CustomException;
import com.example.bloompoem.repository.UserRepository;
import com.example.bloompoem.service.SignService;
import com.example.bloompoem.service.UserService;
import com.example.bloompoem.util.JwtUtil;
import com.example.bloompoem.util.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@CrossOrigin(origins = "http://192.168.1.135:5500/")
@RestController
@RequestMapping(value = "/api/v1/sign")
@RequiredArgsConstructor
@PropertySource("classpath:app.properties")
public class RestSignController {

    @Value("#{environment['jwt.secret']}")
    private String secretKey;
    private final UserService userService;
    private final SignService signService;
    private final UserRepository userRepository;

//     들어오는 데이터 형식 // json
//     "userEmail":"sung8881@naver.com",
//     "userAddress":"인천 중구 제물량로 121-1",
//     "userAddressDetail":"아르누보 506호",
//     "userPhoneNumber":"010-3716-8640",
//     "userName":"임성종"

    @RequestMapping("/sign")
    public ResponseEntity<?> check(Authentication authentication) {

        return ResponseEntity.ok().body(authentication.getName());
    }

    @PostMapping("/sign_up")
    public ResponseEntity<UserSignResponse> singUp(@RequestBody UserSignUpRequest request) {
        userService.createUser(request);
        return UserSignResponse.toResponseEntity(ResponseCode.CREATE);
    }


    @PostMapping("/otp_check")
    public ResponseEntity<UserSignResponse> signUpOtpCheck(@RequestBody UserSignInRequest request) {
        UserEntity testUserEntity = userRepository
                .findByUserEmail(request.getUserEmail())
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_NOT_FOUND));


//        String userEmail = testUserEntity.getUserEmail();
//        String userName = testUserEntity.getUserName();
        String otp = OtpUtil.createOTP(6);
//        userService.userOtpMailSend(userEmail, userName, otp);
//        userService.userUpdateOTP(userEmail, otp);

        return UserSignResponse.toResponseEntity(ResponseCode.SEND_OTP_MAIL);
    }

    @PostMapping("/sign_in")
    public ResponseEntity<?> singIn(@RequestBody UserSignInRequest request, HttpServletRequest req, HttpServletResponse res, HttpSession session) {
        System.out.println(request.getUserOtp());
        System.out.println(request.getUserEmail());

        UserEntity userEntity = userRepository
                .findByUserEmail(request.getUserEmail())
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_NOT_FOUND));
        String xx = signService.login(request);

        session.setAttribute("Authorization", xx);

        Cookie cookie = new Cookie("Authorization", xx);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setPath("/");

        res.addCookie(cookie);

        System.out.println(xx);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

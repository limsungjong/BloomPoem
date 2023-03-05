package com.example.bloompoem.service;


import com.example.bloompoem.domain.dto.UserSignInRequest;
import com.example.bloompoem.entity.TestUserEntity;
import com.example.bloompoem.repository.TestUserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignService {
    Logger logger = LoggerFactory.getLogger(UserService.class);
    private final TestUserRepository testUserRepository;

    //     들어오는 데이터 형식
    //     "userEmail":"sung8881@naver.com",
    //     "userAddress":"인천 중구 제물량로 121-1",
    //     "userAddressDetail":"아르누보 506호",
    //     "userPhoneNumber":"010-3716-8640",
    //     "userName":"임성종"
    // 해야 하는 것
    // 1. 이메일 데이터 형식 다시 체크 formatEmailCheck
    // 2. 핸드폰 번호 형식 체크
    // 3. 이메일 db 중복 체크 duplicateEmailCheck

    public String login(UserSignInRequest dto) throws RuntimeException{
        Optional<TestUserEntity> user =  testUserRepository.findByUserEmail(dto.getUserEmail());
        if(user.isPresent() && user.get().getUserEmail().equals(dto.getUserEmail())) {
            if(user.get().getUserOtp().equals(dto.getUserOtp())) {
                return "login토큰 발행";
            } else {
        // otp 틀림
                throw new RuntimeException("OTP 일치하지 않음");
            }
        } else {
        // 유저 이메일 없음
            throw new RuntimeException(dto.getUserEmail()+"회원 정보 없음");
        }
    }
}

package com.example.bloompoem.service;


import com.example.bloompoem.domain.dto.MailDTO;
import com.example.bloompoem.domain.dto.ResponseCode;
import com.example.bloompoem.domain.dto.UserSignInRequest;
import com.example.bloompoem.entity.TestUserEntity;
import com.example.bloompoem.exception.CustomException;
import com.example.bloompoem.repository.TestUserRepository;
import com.example.bloompoem.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:app.properties")
public class SignService {
    Logger logger = LoggerFactory.getLogger(UserService.class);
    private final TestUserRepository testUserRepository;

    @Value("#{environment['jwt.secret']}")
    private String secretKey;

    private Long expiredMs = 1000 * 60 * 60L;

    private final MailService mailService;

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

    public void sendOtpMail(String userEmail) {
        MailDTO mailDTO = new MailDTO();

        mailService.mailSend(mailDTO);
    }

    public String login(UserSignInRequest dto) throws RuntimeException {
        Optional<TestUserEntity> user = testUserRepository.findByUserEmail(dto.getUserEmail());
        if (user.isPresent()) {
            if (user.get().getUserEmail().equals(dto.getUserEmail())) {
                return JwtUtil.createJwt(dto.getUserEmail(), secretKey, expiredMs);
            } else {
                throw new CustomException(ResponseCode.ACCOUNT_MISMATCH);
            }
        } else {
            throw new CustomException(ResponseCode.MEMBER_NOT_FOUND);
        }
    }
}

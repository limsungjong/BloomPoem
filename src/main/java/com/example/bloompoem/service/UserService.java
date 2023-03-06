package com.example.bloompoem.service;

import com.example.bloompoem.domain.dto.ResponseCode;
import com.example.bloompoem.domain.dto.MailDTO;
import com.example.bloompoem.domain.dto.UserSignUpRequest;
import com.example.bloompoem.entity.TestUserEntity;
import com.example.bloompoem.exception.CustomException;
import com.example.bloompoem.repository.TestUserRepository;
import com.example.bloompoem.util.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    Logger logger = LoggerFactory.getLogger(UserService.class);
    private final TestUserRepository testUserRepository;
    private final MailService mailService;
    private final OtpUtil otpUtil;
    private final PasswordEncoder passwordEncoder;


    public void createUser(UserSignUpRequest request) throws RuntimeException {
        if (ObjectUtils.isEmpty(request.getUserEmail())) {
            logger.error("request 이메일 없음");
            throw new CustomException(ResponseCode.MISSING_PARAMETER_VALUE);
        }
        if (ObjectUtils.isEmpty(request.getUserName())) {
            logger.error("request 이름 없음");
            throw new CustomException(ResponseCode.MISSING_PARAMETER_VALUE);
        }
        if (ObjectUtils.isEmpty(request.getUserAddress())) {
            logger.error("request 주소 없음");
            throw new CustomException(ResponseCode.MISSING_PARAMETER_VALUE);
        }
        if (ObjectUtils.isEmpty(request.getUserAddressDetail())) {
            logger.error("request 상세 주소 없음");
            throw new CustomException(ResponseCode.MISSING_PARAMETER_VALUE);
        }
        if (ObjectUtils.isEmpty(request.getUserPhoneNumber())) {
            logger.error("request 연락처 없음");
            throw new CustomException(ResponseCode.MISSING_PARAMETER_VALUE);
        }

        testUserRepository.findByUserEmail(request.getUserEmail()).ifPresent(user -> {
            throw new CustomException(ResponseCode.DUPLICATE_EMAIL);
        });

        if (!testUserRepository.findByUserEmail(request.getUserEmail()).isPresent()) {
            String userOtp = otpUtil.createOTP(6);
            TestUserEntity userEntity = TestUserEntity
                    .builder()
                    .userEmail(request.getUserEmail())
                    .userAddress(request.getUserAddress())
                    .userAddressDetail(request.getUserAddressDetail())
                    .userName(request.getUserName())
                    .userPhoneNumber(request.getUserPhoneNumber())
                    .userRegDate(new Date())
                    .userStatus('Y')
                    .userOtp(passwordEncoder.encode(userOtp))
                    .userRole("user")
                    .build();
            testUserRepository.save(userEntity);
            userUpdateOTP(request.getUserEmail(), userOtp);
            userOtpMailSend(request.getUserEmail(), request.getUserName(), userOtp);
            return;
        }

        throw new CustomException(ResponseCode.INVALID_REQUEST);
    }

    @Transactional
    public void userUpdateOTP(String userEmail, String userOtp) throws RuntimeException {
        Optional<TestUserEntity> data = testUserRepository.findByUserEmail(userEmail);
        if (data.isPresent()) {
            data.get().setUserOtp(userOtp);
            return;
        }
        throw new RuntimeException("[UserService] Otp 생성중 오류 발생");
    }

    public void userOtpMailSend(String userEmail, String userName, String userOtp) throws RuntimeException {
        MailDTO mailDTO = new MailDTO();
        Optional<TestUserEntity> testUserEntity = testUserRepository.findByUserEmail(userEmail);
        if (testUserEntity.isPresent()) {
            mailDTO.setUserEmail(userEmail);
            mailDTO.setUserName(userName);
            mailDTO.setOTP(userOtp);
            mailDTO.setTitle("블룸포엠에서 보내드리는 인증번호입니다.");
            mailService.mailSend(mailDTO);
            return;
        }
        throw new CustomException(ResponseCode.MEMBER_NOT_FOUND);
    }
}

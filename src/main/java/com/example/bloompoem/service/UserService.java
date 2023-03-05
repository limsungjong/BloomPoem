package com.example.bloompoem.service;

import com.example.bloompoem.domain.dto.MailDTO;
import com.example.bloompoem.domain.dto.UserSignUpRequest;
import com.example.bloompoem.entity.TestUserEntity;
import com.example.bloompoem.repository.TestUserRepository;
import com.example.bloompoem.util.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    Logger logger = LoggerFactory.getLogger(UserService.class);
    private final TestUserRepository testUserRepository;
    private final MailService mailService;
    private final OtpUtil otpUtil;

    private boolean formatEmailCheck(String userEmail) {
        String reg = "([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        return Pattern.matches(reg, userEmail);
    }

    private boolean formatPhoneNubCheck(String userPhoneNum) {
        return true;
    }

    public int createUser(UserSignUpRequest dto) {
        if(formatEmailCheck(dto.getUserEmail())) return -1;

        testUserRepository.findByUserEmail(dto.getUserEmail()).ifPresent(user -> {
            throw new RuntimeException(dto.getUserEmail() + "는 이미 있습니다.");
        });

        TestUserEntity userEntity = TestUserEntity
                .builder()
                .userEmail(dto.getUserEmail())
                .userAddress(dto.getUserAddress())
                .userAddressDetail(dto.getUserAddressDetail())
                .userName(dto.getUserName())
                .userPhoneNumber(dto.getUserPhoneNumber())
                .build();
        testUserRepository.save(userEntity);

        return 0;
    }

    @Transactional
    public String userUpdateOTP(String userEmail) throws RuntimeException {
        String OTP = otpUtil.createOTP(6);
        Optional<TestUserEntity> data = testUserRepository.findByUserEmail(userEmail);
        if(data.isPresent()) {
            data.get().setUserOtp(OTP);
            return OTP;
        }
        throw new RuntimeException();
    }

    @Transactional
    public void userOtpMailSend(UserSignUpRequest dto) throws RuntimeException {
        MailDTO mailDTO = new MailDTO();
        Optional<TestUserEntity> testUserEntity = testUserRepository.findByUserEmail(dto.getUserEmail());
        if (testUserEntity.isPresent()) {
            String OTP = userUpdateOTP(testUserEntity.get().getUserEmail());
            mailDTO.setUserEmail(dto.getUserEmail());
            mailDTO.setUserName(dto.getUserName());
            mailDTO.setOTP(OTP);
            mailDTO.setTitle("블룸포엠에서 보내드리는 인증번호입니다.");
            mailService.mailSend(mailDTO);
            return;
        }
        throw new RuntimeException("[UserService] userOtpMailSend 유저 이메일 존재 하지 않음");
    }
}

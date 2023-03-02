package com.example.bloompoem.service;


import com.example.bloompoem.domain.dto.MailDTO;
import com.example.bloompoem.domain.dto.UserSignUpRequest;
import com.example.bloompoem.entity.TestUserEntity;
import com.example.bloompoem.repository.TestUserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class SignService {
    Logger logger = LoggerFactory.getLogger(UserService.class);

    private final MailService mailService;
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

    private boolean formatEmailCheck(String userEmail) {
        String reg = "([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        return Pattern.matches(reg, userEmail);
    }

    private Optional<TestUserEntity> duplicateEmailCheck(String userEmail) {
        return testUserRepository.findByUserEmail(userEmail);
    }

    public int userCheck(UserSignUpRequest dto) {
        if (dto.getUserAddress().isEmpty()) {
            System.out.println("addressEmpty");
        }

        if (!duplicateEmailCheck(dto.getUserEmail()).isPresent()) {

            if (formatEmailCheck(dto.getUserEmail())) {
                createUser(dto);
                return 0;
            } else {
                logger.error("[SingService] userSignUp format Error -10");
                return -10;
            }
        } else {
            logger.error("[SingService] userSignUp duplicate Error -1");
            return -1;
        }
    }

    private String createOTP(int length) {
        Random rnd = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buf.append((char) (rnd.nextInt(26) + 97));
        }
        return buf.toString();
    }

    @Transactional
    public String userUpdateOTP(String userEmail) throws RuntimeException {
        String OTP = createOTP(6);
        Optional<TestUserEntity> data = testUserRepository.findByUserEmail(userEmail);
        if(data.isPresent()) {
            data.get().setUserOtp(OTP);
            return OTP;
        }
        throw new RuntimeException();
    }

    @Transactional
    public void userCheckMailSend(UserSignUpRequest dto) throws RuntimeException {
        MailDTO mailDTO = new MailDTO();
        Optional<TestUserEntity> testUserEntity = testUserRepository.findByUserEmail(dto.getUserEmail());
        if (testUserEntity.isPresent()) {
            String OTP = userUpdateOTP(testUserEntity.get().getUserEmail());
            mailDTO.setEmailAddress(dto.getUserEmail());
            mailDTO.setUserName(dto.getUserName());
            mailDTO.setOTP(OTP);
            mailDTO.setTitle("블룸포엠에서 보내드리는 인증번호입니다.");
            mailService.mailSend(mailDTO);
            return;
        }
        throw new RuntimeException();
    }

    private String createUser(UserSignUpRequest dto) {

        TestUserEntity user = new TestUserEntity();
        user.setUserEmail(dto.getUserEmail());
        user.setUserName(dto.getUserName());
        user.setUserAddress(dto.getUserAddress());
        user.setUserAddressDetail(dto.getUserAddressDetail());
        user.setUserPhoneNumber(dto.getUserPhoneNumber());
        return testUserRepository.save(user).getUserEmail();
    }
}

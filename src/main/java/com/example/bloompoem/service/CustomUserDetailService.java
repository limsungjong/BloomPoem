package com.example.bloompoem.service;

import com.example.bloompoem.domain.dto.CustomUserDetail;
import com.example.bloompoem.domain.dto.ResponseCode;
import com.example.bloompoem.entity.UserEntity;
import com.example.bloompoem.exception.CustomException;
import com.example.bloompoem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws CustomException {
        UserEntity testUser = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new CustomException(ResponseCode.MEMBER_NOT_FOUND));

        logger.info("input : " + userEmail);
        CustomUserDetail customUserDetail = new CustomUserDetail(testUser);
        logger.info("out userName : " + customUserDetail.getUsername());
        logger.info("out Password : " + customUserDetail.getPassword());

        return customUserDetail;
    }
}

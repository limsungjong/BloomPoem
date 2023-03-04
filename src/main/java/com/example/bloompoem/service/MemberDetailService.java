package com.example.bloompoem.service;

import com.example.bloompoem.domain.dto.MemberDetails;
import com.example.bloompoem.dto.TestUserDTO;
import com.example.bloompoem.entity.TestUserEntity;
import com.example.bloompoem.repository.TestUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberDetailService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(MemberDetailService.class);
    TestUserRepository testUserRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Optional<TestUserEntity> user = testUserRepository.findByUserEmail(userEmail);
        logger.debug("[MemberDetailService] loadUserByUsername user : "+user.get().getUserEmail());
        if(user.isPresent()) {
            return new MemberDetails(TestUserDTO.toDTO(user.get()));
        }
        throw new UsernameNotFoundException("not found userEmail");
    }
}

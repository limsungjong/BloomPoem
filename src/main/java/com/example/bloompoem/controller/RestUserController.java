package com.example.bloompoem.controller;

import com.example.bloompoem.domain.dto.ResponseCode;
import com.example.bloompoem.domain.dto.UserResponse;
import com.example.bloompoem.dto.UserDTO;
import com.example.bloompoem.exception.CustomException;
import com.example.bloompoem.repository.UserRepository;
import com.example.bloompoem.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@ResponseBody
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://172.28.16.1:5500")
public class RestUserController {

    private final UserRepository userRepository;
    @Value("#{environment['jwt.secret']}")
    private String secretKey;
    @PostMapping("/get_user")
    public ResponseEntity<?> user(@CookieValue(value = "Authorization") String token) throws RuntimeException {
        String userEmail = JwtUtil.getUserName(token,secretKey);

        UserDTO userDTO = UserDTO.toDTO(userRepository.findByUserEmail(userEmail).get());

        if (userDTO.getUserEmail().equals(userEmail)) {

            UserResponse res = UserResponse.builder()
                    .userAddress(userDTO.getUserAddress())
                    .userAddressDetail(userDTO.getUserAddressDetail())
                    .userPhoneNumber(userDTO.getUserPhoneNumber())
                    .userEmail(userDTO.getUserEmail())
                    .userName(userDTO.getUserName())
                    .userRole(userDTO.getUserRole())
                    .build();

                    return ResponseEntity.ok().body(res);
        }
        throw new CustomException(ResponseCode.MEMBER_NOT_FOUND);
    }
}

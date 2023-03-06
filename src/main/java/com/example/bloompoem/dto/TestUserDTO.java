package com.example.bloompoem.dto;

import com.example.bloompoem.entity.TestUserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TestUserDTO {
    private String userEmail;
    private String userAddress;
    private String userAddressDetail;
    private String userPhoneNumber;
    private String userName;
    private Date userRegDate;
    private char userStatus;
    private long userCouponNumber;
    private String userOtp;
    private String userRole;


    public static TestUserDTO toDTO(TestUserEntity entity) {
        TestUserDTO testUserDTO = new TestUserDTO();
        testUserDTO.setUserEmail(entity.getUserEmail());
        testUserDTO.setUserAddressDetail(entity.getUserAddressDetail());
        testUserDTO.setUserPhoneNumber(entity.getUserPhoneNumber());
        testUserDTO.setUserName(entity.getUserName());
        testUserDTO.setUserRegDate(entity.getUserRegDate());
        testUserDTO.setUserStatus(entity.getUserStatus());
        testUserDTO.setUserCouponNumber(entity.getUserCouponNumber());
        testUserDTO.setUserOtp(entity.getUserOtp());
        testUserDTO.setUserRole(entity.getUserRole());
        return testUserDTO;
    }
}

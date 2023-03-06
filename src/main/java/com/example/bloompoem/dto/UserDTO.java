package com.example.bloompoem.dto;

import com.example.bloompoem.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDTO {
    private String userEmail;
    private String userAddress;
    private String userAddressDetail;
    private String userPhoneNumber;
    private String userName;
    private Date userRegDate;
    private char userStatus;
    private long userCouponNumber;


    public static UserDTO toDTO(UserEntity entity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserEmail(entity.getUserEmail());
        userDTO.setUserAddressDetail(entity.getUserAddressDetail());
        userDTO.setUserPhoneNumber(entity.getUserPhoneNumber());
        userDTO.setUserName(entity.getUserName());
        userDTO.setUserRegDate(entity.getUserRegDate());
        userDTO.setUserStatus(entity.getUserStatus());
        userDTO.setUserCouponNumber(entity.getUserCouponNumber());
        return userDTO;
    }
}

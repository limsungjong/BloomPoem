package com.example.bloompoem.dto;

import com.example.bloompoem.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
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


    public static UserDTO toDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserEmail(userEntity.getUserEmail());
        userDTO.setUserAddressDetail(userEntity.getUserAddressDetail());
        userDTO.setUserPhoneNumber(userEntity.getUserPhoneNumber());
        userDTO.setUserName(userEntity.getUserName());
        userDTO.setUserRegDate(userEntity.getUserRegDate());
        userDTO.setUserStatus(userEntity.getUserStatus());
        userDTO.setUserCouponNumber(userEntity.getUserCouponNumber());
        return userDTO;
    }
}

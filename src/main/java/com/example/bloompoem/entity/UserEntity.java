package com.example.bloompoem.entity;

import com.example.bloompoem.dto.UserDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "BP_USER")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class UserEntity {
    @Id
    private String userEmail;

    @Column(length = 100)
    private String userAddress;

    @Column(length = 100)
    private String userAddressDetail;

    @Column(length = 20)
    private String userPhoneNumber;

    @Column(length = 20)
    private String userName;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date userRegDate;

    @Column(length = 1)
    private char userStatus;

    @Column(length = 10)
    private long userCouponNumber;

    @Column(length = 10)
    private String userOtp;

    @Column(length = 10)
    private String userRole;

    public static UserEntity toEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserAddress(userDTO.getUserAddress());
        userEntity.setUserAddressDetail(userDTO.getUserAddressDetail());
        userEntity.setUserEmail(userDTO.getUserEmail());
        userEntity.setUserName(userDTO.getUserName());
        userEntity.setUserRegDate(userDTO.getUserRegDate());
        userEntity.setUserStatus(userDTO.getUserStatus());
        userEntity.setUserCouponNumber(userDTO.getUserCouponNumber());
        userEntity.setUserOtp(userDTO.getUserOtp());
        userEntity.setUserRole(userDTO.getUserRole());
        return userEntity;
    }
}

package com.example.bloompoem.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@Table(name = "TEST_USER")
@NoArgsConstructor
@AllArgsConstructor
public class TestUserEntity {
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
    private Date userRegDate;

    @Column(length = 1)
    private char userStatus;

    @Column(length = 10)
    private int userCouponNumber;
    @Column(length = 10)
    private String userOtp;

    @Column(length = 10)
    private String userRole;
}

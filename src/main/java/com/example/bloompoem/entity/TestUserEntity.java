package com.example.bloompoem.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@Table(name = "TEST_USER")
@NoArgsConstructor
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
    private String userOtp;
}

package com.example.bloompoem.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TEST_USER")
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

    @Column(nullable=false, columnDefinition = "date default sysdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date userRegDate = new Date();

    @Column(length = 1)
    private char userStatus = 'Y';

    @Column(length = 12)
    private long userCouponNumber;

    @Column
    @Setter
    private String userOtp;
}

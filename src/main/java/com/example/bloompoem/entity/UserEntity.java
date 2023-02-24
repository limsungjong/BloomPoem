package com.example.bloompoem.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "BP_USER")
@NoArgsConstructor
public class UserEntity {
    @Id
    String userEmail;

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
}

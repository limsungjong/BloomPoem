package com.example.bloompoem.domain;

import lombok.Getter;

@Getter
public class UserSignUpRequest {
    private String userEmail;

    private String userAddress;

    private String userAddressDetail;

    private String userPhoneNumber;

    private String userName;
}

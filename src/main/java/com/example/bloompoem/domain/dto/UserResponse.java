package com.example.bloompoem.domain.dto;

import lombok.Builder;

import java.util.Date;

@Builder
public class UserResponse {
    private String userEmail;
    private String userAddress;
    private String userAddressDetail;
    private String userPhoneNumber;
    private String userName;
    private String userRole;

    public UserResponse() {

    }
}

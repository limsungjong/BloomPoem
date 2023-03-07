package com.example.bloompoem.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponse {
    private String userEmail;
    private String userAddress;
    private String userAddressDetail;
    private String userPhoneNumber;
    private String userName;
    private String userRole;

}

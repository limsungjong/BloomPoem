package com.example.bloompoem.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignInRequest {
    private String userEmail;
    private String userOtp;
}

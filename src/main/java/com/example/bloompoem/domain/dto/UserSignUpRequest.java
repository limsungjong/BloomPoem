package com.example.bloompoem.domain.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;


@Getter
public class UserSignUpRequest {
//    @NotBlank
    @Pattern(regexp = "([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")
    private String userEmail;

//    @NotBlank
    private String userAddress;

//    @NotBlank
    private String userAddressDetail;

//    @NotBlank
    @Pattern(regexp = "(0[2-8][0-5]?|01[01346-9])-?([1-9]{1}[0-9]{2,3})-?([0-9]{4})$")
    private String userPhoneNumber;

//    @NotBlank
    private String userName;
}

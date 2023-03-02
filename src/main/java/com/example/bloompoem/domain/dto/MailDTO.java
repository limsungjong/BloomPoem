package com.example.bloompoem.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailDTO {
    private String emailAddress;
    private String title;
    private String userName;
    private String OTP;
}

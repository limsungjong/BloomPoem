package com.example.bloompoem.domain.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserSignResponse {
    private final LocalDateTime timeStamp = LocalDateTime.now();
    private final int status;
    private final String message;
    private final String code;
    private final String token;

    public static ResponseEntity<UserSignResponse> toResponseEntity(ResponseCode responseCode,String jwt) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(UserSignResponse.builder()
                        .status(responseCode.getHttpStatus().value())
                        .token(jwt)
                        .code(responseCode.name())
                        .message(responseCode.getDetail())
                        .build()
                );
    }
}

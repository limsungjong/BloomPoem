package com.example.bloompoem.exception;

import com.example.bloompoem.domain.dto.ResponseCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    //https://bcp0109.tistory.com/303
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String code;
    private final String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ResponseCode responseCode) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(responseCode.getHttpStatus().value())
                        .error(responseCode.getHttpStatus().name())
                        .code(responseCode.name())
                        .message(responseCode.getDetail())
                        .build()
                );
    }
}

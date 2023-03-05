package com.example.bloompoem.exception;

import com.example.bloompoem.domain.dto.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException implements Serializable {

    private final ErrorCode errorCode;
}

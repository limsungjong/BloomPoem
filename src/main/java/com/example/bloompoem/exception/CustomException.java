package com.example.bloompoem.exception;

import com.example.bloompoem.domain.dto.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException implements Serializable {

    private final ResponseCode responseCode;
}

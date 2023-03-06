package com.example.bloompoem.exception;

import com.example.bloompoem.domain.dto.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

    private final ResponseCode responseCode;
}

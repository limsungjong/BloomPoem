package com.example.bloompoem.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import static com.example.bloompoem.domain.dto.ErrorCode.DUPLICATE_RESOURCE;

@RestControllerAdvice
public class ExceptionManager extends ResponseEntityExceptionHandler {
    Logger logger = LoggerFactory.getLogger(ExceptionManager.class);

    // handleDataException : hibernate 관련 에러 처리
    @ExceptionHandler(value = { ConstraintViolationException.class, DataIntegrityViolationException.class })
    protected ResponseEntity<ErrorResponse> handleDataException() {
        logger.error("handleDataException throw Exception : {}", DUPLICATE_RESOURCE);
        return ErrorResponse.toResponseEntity(DUPLICATE_RESOURCE);
    }

    // handleCustomException : customException 관련 에러 처리
    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        logger.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}

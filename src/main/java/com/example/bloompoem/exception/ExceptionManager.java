package com.example.bloompoem.exception;

import com.example.bloompoem.domain.dto.UserSignResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManager {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException ex) {
        UserSignResponse res = new UserSignResponse();
        res.setReason(ex.getMessage());
        return ResponseEntity.badRequest().body(res);
    }

    // UserSignRequest Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UserSignResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        UserSignResponse res = new UserSignResponse();
        ex.getBindingResult().getAllErrors().forEach(
                c -> {
                    res.setStatus(c.getCode());
                    res.setReason(c.getDefaultMessage());
                }
        );
        return ResponseEntity.badRequest().body(res);
    }
}

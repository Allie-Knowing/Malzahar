package com.foreveryone.knowing.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(KnowingException.class)
    public ResponseEntity<ErrorResponse> handleKnowingException(KnowingException e) {
        String errorDescription = e.getMessage();
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse res = ErrorResponse.of(errorCode, errorDescription);

        return new ResponseEntity<>(res, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleException(AccessDeniedException e) {
        ErrorCode errorCode = ErrorCode.ACCESS_DENIED;
        String errorDescription = "접근 거부";
        ErrorResponse res = ErrorResponse.of(errorCode, errorDescription);
        e.printStackTrace();

        return new ResponseEntity<>(res, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        String errorDescription = "서버 오류";
        ErrorResponse res = ErrorResponse.of(errorCode, errorDescription);
        e.printStackTrace();

        return new ResponseEntity<>(res, HttpStatus.valueOf(errorCode.getStatus()));
    }
}

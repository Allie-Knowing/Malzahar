package com.foreveryone.knowing.error;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(KnowingException.class)
    public ResponseEntity<ErrorResponse> handleKnowingException(KnowingException e) {
        String errorDescription = e.getMessage();
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse res = ErrorResponse.of(errorCode, errorDescription);

        System.out.println("error message = " + e.getMessage());

        return new ResponseEntity<>(res, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class, MissingServletRequestPartException.class,
            MissingServletRequestParameterException.class, MultipartException.class,
            HttpMessageNotReadableException.class, MissingRequestHeaderException.class})
    public ResponseEntity<ErrorResponse> badRequestException(Exception e) {
        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        String errorDescription = e.getMessage();
        ErrorResponse res = ErrorResponse.of(errorCode, errorDescription);

        System.out.println("errorDescription = " + errorDescription);

        return new ResponseEntity<>(res, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignException(FeignException e) {
        String errorDescription = e.getMessage();
        ErrorResponse res = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, errorDescription);

        System.out.println("errorDescription = " + errorDescription);

        return new ResponseEntity<>(res, HttpStatus.valueOf(e.status()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleException(AccessDeniedException e) {
        ErrorCode errorCode = ErrorCode.ACCESS_DENIED;
        String errorDescription = "접근 거부";
        ErrorResponse res = ErrorResponse.of(errorCode, errorDescription);

        System.out.println(errorDescription);

        return new ResponseEntity<>(res, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        String errorDescription = e.getMessage();
        ErrorResponse res = ErrorResponse.of(errorCode, errorDescription);

        e.printStackTrace();
        System.out.println("errorDescription = " + errorDescription);

        return new ResponseEntity<>(res, HttpStatus.valueOf(errorCode.getStatus()));
    }
}

package com.foreveryone.knowing.error;

import lombok.Getter;

@Getter
public class KnowingException extends RuntimeException{

    private ErrorCode errorCode;

    public KnowingException(ErrorCode errorcode, String message) {
        super(message);
        this.errorCode = errorcode;
    }
}

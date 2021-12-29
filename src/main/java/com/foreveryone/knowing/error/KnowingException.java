package com.foreveryone.knowing.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KnowingException extends RuntimeException{

    private ErrorCode errorCode;

    public KnowingException(String message) {
        super(message);
    }
}

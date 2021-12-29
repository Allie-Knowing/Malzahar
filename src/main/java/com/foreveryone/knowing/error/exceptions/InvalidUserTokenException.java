package com.foreveryone.knowing.error.exceptions;

import com.foreveryone.knowing.error.ErrorCode;
import com.foreveryone.knowing.error.KnowingException;
import lombok.Getter;

@Getter
public class InvalidUserTokenException extends KnowingException {

    public InvalidUserTokenException(String message) {
        super(ErrorCode.INVALID_USER_TOKEN, message);
    }
}

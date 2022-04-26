package com.foreveryone.knowing.error.exceptions;

import com.foreveryone.knowing.error.ErrorCode;
import com.foreveryone.knowing.error.KnowingException;

public class InvalidIdTokenException extends KnowingException {

    public InvalidIdTokenException(String message) {
        super(ErrorCode.INVALID_ID_TOKEN, message);
    }
}

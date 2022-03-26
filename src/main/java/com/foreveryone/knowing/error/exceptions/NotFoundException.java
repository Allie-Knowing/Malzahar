package com.foreveryone.knowing.error.exceptions;

import com.foreveryone.knowing.error.ErrorCode;
import com.foreveryone.knowing.error.KnowingException;

public class NotFoundException extends KnowingException {

    public NotFoundException(String message) {
        super(ErrorCode.NOT_FOUND, message);
    }
}

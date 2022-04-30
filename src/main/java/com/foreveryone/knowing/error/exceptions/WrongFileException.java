package com.foreveryone.knowing.error.exceptions;

import com.foreveryone.knowing.error.ErrorCode;
import com.foreveryone.knowing.error.KnowingException;

public class WrongFileException extends KnowingException {
    public WrongFileException(String message) {
        super(ErrorCode.INVALID_INPUT_VALUE, message);
    }
}

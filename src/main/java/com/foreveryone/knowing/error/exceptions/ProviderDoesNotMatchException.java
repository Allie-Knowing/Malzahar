package com.foreveryone.knowing.error.exceptions;

import com.foreveryone.knowing.error.ErrorCode;
import com.foreveryone.knowing.error.KnowingException;

public class ProviderDoesNotMatchException extends KnowingException {

    public ProviderDoesNotMatchException(String message) {
        super(ErrorCode.PROVIDER_DOES_NOT_MATCH, message);
    }
}

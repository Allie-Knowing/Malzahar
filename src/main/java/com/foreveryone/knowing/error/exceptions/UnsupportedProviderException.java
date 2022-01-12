package com.foreveryone.knowing.error.exceptions;

import com.foreveryone.knowing.error.ErrorCode;
import com.foreveryone.knowing.error.KnowingException;

public class UnsupportedProviderException extends KnowingException {
    @Override
    public ErrorCode getErrorCode() {
        return super.getErrorCode();
    }

    public UnsupportedProviderException(String message) {
        super(ErrorCode.UNSUPPORTED_PROVIDER, message);
    }
}

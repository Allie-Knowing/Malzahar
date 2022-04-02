package com.foreveryone.knowing.error.exceptions;

import com.foreveryone.knowing.error.ErrorCode;
import com.foreveryone.knowing.error.KnowingException;

public class OauthBadRequestException extends KnowingException {

    public OauthBadRequestException(String message) {
        super(ErrorCode.REQUIRE_PARAMETER_MISSING, message);
    }
}

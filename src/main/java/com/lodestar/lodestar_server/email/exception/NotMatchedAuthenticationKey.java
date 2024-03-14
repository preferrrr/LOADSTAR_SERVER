package com.lodestar.lodestar_server.email.exception;

import com.lodestar.lodestar_server.common.exception.ExceptionCode;
import lombok.Getter;

import static com.lodestar.lodestar_server.email.exception.MailExceptionCode.*;

public class NotMatchedAuthenticationKey extends RuntimeException{
    @Getter
    private final ExceptionCode exceptionCode;

    public NotMatchedAuthenticationKey() {
        super(NOT_MATCHED_AUTHENTICATION_KEY.getMessage());
        this.exceptionCode = NOT_MATCHED_AUTHENTICATION_KEY;
    }
}

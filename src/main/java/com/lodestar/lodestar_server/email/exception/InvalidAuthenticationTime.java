package com.lodestar.lodestar_server.email.exception;

import com.lodestar.lodestar_server.common.exception.ExceptionCode;
import lombok.Getter;

import static com.lodestar.lodestar_server.email.exception.MailExceptionCode.*;

public class InvalidAuthenticationTime extends RuntimeException {
    @Getter
    private final ExceptionCode exceptionCode;

    public InvalidAuthenticationTime() {
        super(INVALID_AUTHENTICATION_TIME.getMessage());
        this.exceptionCode = INVALID_AUTHENTICATION_TIME;
    }
}

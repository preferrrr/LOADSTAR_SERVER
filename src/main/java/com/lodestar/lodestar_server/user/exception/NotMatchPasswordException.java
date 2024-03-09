package com.lodestar.lodestar_server.user.exception;

import com.lodestar.lodestar_server.exception.ExceptionCode;
import lombok.Getter;

import static com.lodestar.lodestar_server.user.exception.UserExceptionCode.*;

public class NotMatchPasswordException extends RuntimeException {
    @Getter
    private final ExceptionCode exceptionCode;

    public NotMatchPasswordException() {
        super(NOT_MATCH_PASSWORD.getMessage());
        this.exceptionCode = NOT_MATCH_PASSWORD;
    }
}

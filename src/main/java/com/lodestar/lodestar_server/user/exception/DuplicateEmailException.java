package com.lodestar.lodestar_server.user.exception;

import com.lodestar.lodestar_server.exception.ExceptionCode;
import lombok.Getter;

import static com.lodestar.lodestar_server.user.exception.UserExceptionCode.*;

public class DuplicateEmailException extends RuntimeException {
    @Getter
    private final ExceptionCode exceptionCode;

    public DuplicateEmailException() {
        super(DUPLICATE_EMAIL.getMessage());
        this.exceptionCode = DUPLICATE_EMAIL;
    }
}

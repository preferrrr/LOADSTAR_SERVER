package com.lodestar.lodestar_server.user.exception;

import com.lodestar.lodestar_server.common.exception.ExceptionCode;
import lombok.Getter;

import static com.lodestar.lodestar_server.user.exception.UserExceptionCode.*;

public class UserNotFoundByEmailException extends RuntimeException {
    @Getter
    private final ExceptionCode exceptionCode;

    public UserNotFoundByEmailException() {
        super(USER_NOT_FOUND_BY_EMAIL.getMessage());
        this.exceptionCode = USER_NOT_FOUND_BY_EMAIL;
    }
}

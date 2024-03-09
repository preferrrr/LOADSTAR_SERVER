package com.lodestar.lodestar_server.user.exception;

import com.lodestar.lodestar_server.exception.ExceptionCode;
import lombok.Getter;

import static com.lodestar.lodestar_server.user.exception.UserExceptionCode.*;

public class UserNotFoundByIdException extends RuntimeException {
    @Getter
    private final ExceptionCode exceptionCode;

    public UserNotFoundByIdException() {
        super(USER_NOT_FOUND_BY_ID.getMessage());
        this.exceptionCode = USER_NOT_FOUND_BY_ID;
    }
}

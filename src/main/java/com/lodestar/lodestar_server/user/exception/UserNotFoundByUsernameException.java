package com.lodestar.lodestar_server.user.exception;

import com.lodestar.lodestar_server.exception.ExceptionCode;
import lombok.Getter;

import static com.lodestar.lodestar_server.user.exception.UserExceptionCode.*;

public class UserNotFoundByUsernameException extends RuntimeException {
    @Getter
    private final ExceptionCode exceptionCode;

    public UserNotFoundByUsernameException() {
        super(USER_NOT_FOUND_BY_USERNAME.getMessage());
        this.exceptionCode = USER_NOT_FOUND_BY_USERNAME;
    }
}

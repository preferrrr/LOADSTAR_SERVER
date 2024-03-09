package com.lodestar.lodestar_server.email.exception;

import com.lodestar.lodestar_server.exception.ExceptionCode;
import lombok.Getter;

import static com.lodestar.lodestar_server.email.exception.MailExceptionCode.*;

public class NotMatchedUsernameException extends RuntimeException {
    @Getter
    private final ExceptionCode exceptionCode;

    public NotMatchedUsernameException() {
        super(NOT_MATCHED_USERNAME.getMessage());
        this.exceptionCode = NOT_MATCHED_USERNAME;
    }
}

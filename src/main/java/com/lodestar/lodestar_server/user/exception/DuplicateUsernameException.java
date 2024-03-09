package com.lodestar.lodestar_server.user.exception;

import com.lodestar.lodestar_server.exception.ExceptionCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.lodestar.lodestar_server.user.exception.UserExceptionCode.*;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateUsernameException extends RuntimeException {

    @Getter
    private final ExceptionCode exceptionCode;

    public DuplicateUsernameException() {
        super(DUPLICATE_USERNAME.getMessage());
        this.exceptionCode = DUPLICATE_USERNAME;
    }
}

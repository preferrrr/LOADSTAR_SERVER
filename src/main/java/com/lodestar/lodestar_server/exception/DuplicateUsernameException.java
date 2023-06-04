package com.lodestar.lodestar_server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateUsernameException extends RuntimeException {

    @Getter
    private final String NAME;

    public DuplicateUsernameException(String msg) {
        super(msg);
        NAME = "DuplicateUsernameException";
    }
}

package com.lodestar.lodestar_server.exception;

import lombok.Getter;

public class NotExistUserException extends RuntimeException{
    @Getter
    private final String NAME;

    public NotExistUserException(String msg) {
        super(msg);
        NAME = "NotExistUserException";
    }
}

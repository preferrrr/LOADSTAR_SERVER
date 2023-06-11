package com.lodestar.lodestar_server.exception;

import lombok.Getter;

public class InvalidTokenException extends RuntimeException{
    @Getter
    private final String NAME;

    public InvalidTokenException(String msg) {
        super(msg);
        NAME = "InvalidTokenException";
    }
}

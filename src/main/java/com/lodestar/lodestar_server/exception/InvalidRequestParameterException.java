package com.lodestar.lodestar_server.exception;

import lombok.Getter;

@Getter
public class InvalidRequestParameterException extends RuntimeException {
    private final String NAME;

    public InvalidRequestParameterException(String message) {
        super(message);
        NAME = "InvalidRequestParameterException";
    }
}

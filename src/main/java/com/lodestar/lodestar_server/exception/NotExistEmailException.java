package com.lodestar.lodestar_server.exception;

import lombok.Getter;

public class NotExistEmailException extends RuntimeException{
    @Getter
    private final String NAME;

    public NotExistEmailException(String msg) {
        super(msg);
        NAME = "NotExistEmailException";
    }

}

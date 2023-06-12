package com.lodestar.lodestar_server.exception;

import lombok.Getter;

public class ExistUsernameException extends RuntimeException{
    @Getter
    private final String NAME;

    public ExistUsernameException(String msg) {
        super(msg);
        NAME = "ExistUsernameException";
    }
}

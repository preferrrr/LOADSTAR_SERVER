package com.lodestar.lodestar_server.user.exception;

import lombok.Getter;

public class NotMatchPasswordException extends RuntimeException{
    @Getter
    private final String NAME;

    public NotMatchPasswordException() {
        NAME = "NotMatchPasswordException";
    }
}

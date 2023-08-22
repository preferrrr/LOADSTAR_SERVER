package com.lodestar.lodestar_server.exception;

import lombok.Getter;

public class NotCheckException extends RuntimeException{
    @Getter
    private final String NAME;

    public NotCheckException(String msg) {
        super(msg);
        NAME = "NotCheckEmailException";
    }

}

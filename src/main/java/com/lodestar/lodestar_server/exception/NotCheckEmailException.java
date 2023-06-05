package com.lodestar.lodestar_server.exception;

import lombok.Getter;

public class NotCheckEmailException extends RuntimeException{
    @Getter
    private final String NAME;

    public NotCheckEmailException(String msg) {
        super(msg);
        NAME = "NotCheckEmailException";
    }

}

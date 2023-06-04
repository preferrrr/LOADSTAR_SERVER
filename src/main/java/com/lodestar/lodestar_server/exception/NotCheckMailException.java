package com.lodestar.lodestar_server.exception;

import lombok.Getter;

public class NotCheckMailException extends RuntimeException{
    @Getter
    private final String NAME;

    public NotCheckMailException(String msg) {
        super(msg);
        NAME = "NotCheckEmailException";
    }

}

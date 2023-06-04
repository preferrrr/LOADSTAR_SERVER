package com.lodestar.lodestar_server.exception;

import lombok.Getter;

public class DuplicateMailException extends RuntimeException{
    @Getter
    private final String NAME;

    public DuplicateMailException(String msg) {
        super(msg);
        NAME = "DuplicateMailException";
    }
}

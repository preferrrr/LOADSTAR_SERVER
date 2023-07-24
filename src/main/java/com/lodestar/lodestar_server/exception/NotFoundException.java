package com.lodestar.lodestar_server.exception;

import lombok.Getter;

public class NotFoundException extends RuntimeException{
    @Getter
    private final String NAME;

    public NotFoundException(String msg) {
        super(msg);
        NAME = "NotFoundException";
    }
}

package com.lodestar.lodestar_server.exception;

import lombok.Getter;

public class DuplicateEmailException extends RuntimeException{
    @Getter
    private final String NAME;

    public DuplicateEmailException(String msg) {
        super(msg);
        NAME = "DuplicateMailException";
    }
}

package com.lodestar.lodestar_server.user.exception;

import lombok.Getter;

public class DuplicateEmailException extends RuntimeException{
    @Getter
    private final String NAME;

    public DuplicateEmailException() {
        NAME = "DuplicateMailException";
    }
}

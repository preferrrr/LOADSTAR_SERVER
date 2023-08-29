package com.lodestar.lodestar_server.exception;

import lombok.Getter;

public class DuplicateCareerException extends RuntimeException{
    @Getter
    private final String NAME;

    public DuplicateCareerException(String msg) {
        super(msg);
        NAME = "DuplicateCareerException";
    }
}

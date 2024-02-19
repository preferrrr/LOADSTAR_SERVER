package com.lodestar.lodestar_server.career.exception;

import lombok.Getter;

public class DuplicateCareerException extends RuntimeException{
    @Getter
    private final String NAME;

    public DuplicateCareerException() {
        NAME = "DuplicateCareerException";
    }
}

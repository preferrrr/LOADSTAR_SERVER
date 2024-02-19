package com.lodestar.lodestar_server.career.exception;

import lombok.Getter;

public class UnauthorizedDeleteCareerException extends RuntimeException {
    @Getter
    private final String NAME;

    public UnauthorizedDeleteCareerException() {
        NAME = "UnauthorizedDeleteCareerException";
    }
}

package com.lodestar.lodestar_server.board.exception;

import lombok.Getter;

public class UnauthorizedModifyException extends RuntimeException{
    @Getter
    private final String NAME;

    public UnauthorizedModifyException() {
        NAME = "UnauthorizedModifyException";
    }
}

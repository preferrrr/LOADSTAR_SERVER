package com.lodestar.lodestar_server.board.exception;

import lombok.Getter;

public class UnauthorizedDeleteException extends RuntimeException{
    @Getter
    private final String NAME;

    public UnauthorizedDeleteException() {
        NAME = "UnauthorizedDeletionException";
    }
}

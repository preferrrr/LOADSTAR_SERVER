package com.lodestar.lodestar_server.comment.exception;

import lombok.Getter;

public class UnauthorizedDeleteCommentException extends RuntimeException {
    @Getter
    private final String NAME;

    public UnauthorizedDeleteCommentException() {
        NAME = "UnauthorizedDeleteCommentException";
    }
}

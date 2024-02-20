package com.lodestar.lodestar_server.comment.exception;

import lombok.Getter;

public class UnauthorizedModifyCommentException extends RuntimeException {
    @Getter
    private final String NAME;

    public UnauthorizedModifyCommentException() {
        NAME = "UnauthorizedModifyCommentException";
    }
}

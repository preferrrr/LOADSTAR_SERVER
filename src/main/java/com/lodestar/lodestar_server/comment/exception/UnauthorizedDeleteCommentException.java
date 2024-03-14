package com.lodestar.lodestar_server.comment.exception;

import com.lodestar.lodestar_server.common.exception.ExceptionCode;
import lombok.Getter;

import static com.lodestar.lodestar_server.comment.exception.CommentExceptionCode.*;

public class UnauthorizedDeleteCommentException extends RuntimeException {
    @Getter
    private final ExceptionCode exceptionCode;

    public UnauthorizedDeleteCommentException() {
        super(UNAUTHORIZED_DELETE_COMMENT.getMessage());
        this.exceptionCode = UNAUTHORIZED_DELETE_COMMENT;
    }
}

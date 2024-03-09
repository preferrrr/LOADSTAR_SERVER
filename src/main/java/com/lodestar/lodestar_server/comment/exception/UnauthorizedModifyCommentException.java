package com.lodestar.lodestar_server.comment.exception;

import com.lodestar.lodestar_server.exception.ExceptionCode;
import lombok.Getter;

import static com.lodestar.lodestar_server.comment.exception.CommentExceptionCode.*;

public class UnauthorizedModifyCommentException extends RuntimeException {
    @Getter
    private final ExceptionCode exceptionCode;

    public UnauthorizedModifyCommentException() {
        super(UNAUTHORIZED_MODIFY_COMMENT.getMessage());
        this.exceptionCode = UNAUTHORIZED_MODIFY_COMMENT;
    }
}

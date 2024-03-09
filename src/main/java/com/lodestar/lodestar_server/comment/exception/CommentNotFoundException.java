package com.lodestar.lodestar_server.comment.exception;

import com.lodestar.lodestar_server.exception.ExceptionCode;
import lombok.Getter;

import static com.lodestar.lodestar_server.comment.exception.CommentExceptionCode.*;

public class CommentNotFoundException extends RuntimeException{
    @Getter
    private final ExceptionCode exceptionCode;

    public CommentNotFoundException() {
        super(COMMENT_NOT_FOUND.getMessage());
        this.exceptionCode = COMMENT_NOT_FOUND;
    }
}

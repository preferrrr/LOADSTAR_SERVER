package com.lodestar.lodestar_server.board.exception;

import com.lodestar.lodestar_server.common.exception.ExceptionCode;
import lombok.Getter;

import static com.lodestar.lodestar_server.board.exception.BoardExceptionCode.UNAUTHORIZED_DELETE;

public class UnauthorizedDeleteException extends RuntimeException {
    @Getter
    private final ExceptionCode exceptionCode;

    public UnauthorizedDeleteException() {
        super(UNAUTHORIZED_DELETE.getMessage());
        this.exceptionCode = UNAUTHORIZED_DELETE;
    }
}

package com.lodestar.lodestar_server.board.exception;

import com.lodestar.lodestar_server.common.exception.ExceptionCode;
import lombok.Getter;

import static com.lodestar.lodestar_server.board.exception.BoardExceptionCode.UNAUTHORIZED_MODIFY;

public class UnauthorizedModifyException extends RuntimeException{
    @Getter
    private final ExceptionCode exceptionCode;

    public UnauthorizedModifyException() {
        super(UNAUTHORIZED_MODIFY.getMessage());
        this.exceptionCode = UNAUTHORIZED_MODIFY;
    }
}

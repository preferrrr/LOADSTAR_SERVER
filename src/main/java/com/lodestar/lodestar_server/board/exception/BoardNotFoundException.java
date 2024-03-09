package com.lodestar.lodestar_server.board.exception;

import com.lodestar.lodestar_server.exception.ExceptionCode;
import lombok.Getter;

import static com.lodestar.lodestar_server.board.exception.BoardExceptionCode.BOARD_NOT_FOUND;

public class BoardNotFoundException extends RuntimeException{

    @Getter
    private final ExceptionCode exceptionCode;

    public BoardNotFoundException() {
        super(BOARD_NOT_FOUND.getMessage());
        this.exceptionCode = BOARD_NOT_FOUND;
    }
}

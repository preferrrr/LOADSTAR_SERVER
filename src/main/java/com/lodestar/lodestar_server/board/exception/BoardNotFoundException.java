package com.lodestar.lodestar_server.board.exception;

import lombok.Getter;

public class BoardNotFoundException extends RuntimeException{
    @Getter
    private final String NAME;

    public BoardNotFoundException() {
        NAME = "BoardNotFoundException";
    }
}

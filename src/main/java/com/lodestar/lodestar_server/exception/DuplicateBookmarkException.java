package com.lodestar.lodestar_server.exception;

import lombok.Getter;

public class DuplicateBookmarkException extends RuntimeException{
    @Getter
    private final String NAME;

    public DuplicateBookmarkException(String msg) {
        super(msg);
        NAME = "DuplicateBookmarkException";
    }
}

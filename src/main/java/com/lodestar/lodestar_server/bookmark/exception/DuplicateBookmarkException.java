package com.lodestar.lodestar_server.bookmark.exception;

import lombok.Getter;

public class DuplicateBookmarkException extends RuntimeException{
    @Getter
    private final String NAME;

    public DuplicateBookmarkException() {
        NAME = "DuplicateBookmarkException";
    }
}

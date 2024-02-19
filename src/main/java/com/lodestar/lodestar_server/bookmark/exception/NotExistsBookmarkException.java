package com.lodestar.lodestar_server.bookmark.exception;

import lombok.Getter;

public class NotExistsBookmarkException extends RuntimeException{
    @Getter
    private final String NAME;

    public NotExistsBookmarkException() {
        NAME = "NotExistBookmarkException";
    }
}

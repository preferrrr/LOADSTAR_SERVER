package com.lodestar.lodestar_server.bookmark.exception;

import com.lodestar.lodestar_server.common.exception.ExceptionCode;
import lombok.Getter;

import static com.lodestar.lodestar_server.bookmark.exception.BookmarkExceptionCode.NOT_EXIST_BOOKMARK;

public class NotExistsBookmarkException extends RuntimeException{
    @Getter
    private final ExceptionCode exceptionCode;

    public NotExistsBookmarkException() {
        super(NOT_EXIST_BOOKMARK.getMessage());
        this.exceptionCode = NOT_EXIST_BOOKMARK;
    }
}

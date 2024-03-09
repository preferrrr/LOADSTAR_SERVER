package com.lodestar.lodestar_server.bookmark.exception;

import com.lodestar.lodestar_server.exception.ExceptionCode;
import lombok.Getter;

import static com.lodestar.lodestar_server.bookmark.exception.BookmarkExceptionCode.DUPLICATE_BOOKMARK;

public class DuplicateBookmarkException extends RuntimeException{
    @Getter
    private final ExceptionCode exceptionCode;

    public DuplicateBookmarkException() {
        super(DUPLICATE_BOOKMARK.getMessage());
        this.exceptionCode = DUPLICATE_BOOKMARK;
    }
}

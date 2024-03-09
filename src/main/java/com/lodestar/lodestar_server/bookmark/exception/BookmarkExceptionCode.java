package com.lodestar.lodestar_server.bookmark.exception;

import com.lodestar.lodestar_server.exception.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum BookmarkExceptionCode implements ExceptionCode {

    DUPLICATE_BOOKMARK(HttpStatus.CONFLICT, "이미 북마크한 게시글입니다."),
    NOT_EXIST_BOOKMARK(HttpStatus.BAD_REQUEST, "북마크한 게시글이 아닙니다.");

    private final HttpStatus httpStatus;
    private final String message;
}

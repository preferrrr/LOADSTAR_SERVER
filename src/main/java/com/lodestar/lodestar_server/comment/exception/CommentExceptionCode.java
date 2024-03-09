package com.lodestar.lodestar_server.comment.exception;

import com.lodestar.lodestar_server.exception.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum CommentExceptionCode implements ExceptionCode {

    COMMENT_NOT_FOUND(NOT_FOUND, "존재하지 않는 댓글입니다."),
    UNAUTHORIZED_DELETE_COMMENT(FORBIDDEN, "댓글을 삭제할 권한이 없습니다."),
    UNAUTHORIZED_MODIFY_COMMENT(FORBIDDEN, "댓글을 수정할 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}

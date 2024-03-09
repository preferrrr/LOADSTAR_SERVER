package com.lodestar.lodestar_server.comment.exception;

import com.lodestar.lodestar_server.exception.ExceptionCode;
import com.lodestar.lodestar_server.exception.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CommentExceptionHandler {

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleCommentNotFoundException(final CommentNotFoundException e) {

        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("[comment not found exception] {}", e.getMessage());

        return new ResponseEntity<>(
                ExceptionResponse.of(exceptionCode),
                exceptionCode.getHttpStatus()
        );
    }

    @ExceptionHandler(UnauthorizedDeleteCommentException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedDeleteCommentException(final UnauthorizedDeleteCommentException e) {

        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("[unauthorized delete comment exception] {}", e.getMessage());

        return new ResponseEntity<>(
                ExceptionResponse.of(exceptionCode),
                exceptionCode.getHttpStatus()
        );
    }

    @ExceptionHandler(UnauthorizedModifyCommentException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedModifyCommentException(final UnauthorizedModifyCommentException e) {

        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("{}", e.getMessage());

        return new ResponseEntity<>(
                ExceptionResponse.of(exceptionCode),
                exceptionCode.getHttpStatus()
        );
    }
}

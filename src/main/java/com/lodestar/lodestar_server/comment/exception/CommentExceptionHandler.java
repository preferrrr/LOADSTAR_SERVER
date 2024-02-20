package com.lodestar.lodestar_server.comment.exception;

import com.lodestar.lodestar_server.career.exception.DuplicateCareerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CommentExceptionHandler {

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<?> handleCommentNotFoundException(final CommentNotFoundException e) {

        log.error("{}", e.getMessage());

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedDeleteCommentException.class)
    public ResponseEntity<?> handleUnauthorizedDeleteCommentException(final UnauthorizedDeleteCommentException e) {

        log.error("{}", e.getMessage());

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UnauthorizedModifyCommentException.class)
    public ResponseEntity<?> handleUnauthorizedModifyCommentException(final UnauthorizedModifyCommentException e) {

        log.error("{}", e.getMessage());

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}

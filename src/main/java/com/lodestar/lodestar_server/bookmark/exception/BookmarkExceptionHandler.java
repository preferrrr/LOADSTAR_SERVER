package com.lodestar.lodestar_server.bookmark.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class BookmarkExceptionHandler {

    @ExceptionHandler(DuplicateBookmarkException.class)
    public ResponseEntity<?> handleDuplicateBookmarkException(final DuplicateBookmarkException e) {

        log.error("{}", e.getMessage());

        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotExistsBookmarkException.class)
    public ResponseEntity<?> handleNotExistBookmarkException(final NotExistsBookmarkException e) {

        log.error("{}", e.getMessage());

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

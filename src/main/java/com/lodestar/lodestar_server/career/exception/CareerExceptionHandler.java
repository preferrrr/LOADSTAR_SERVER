package com.lodestar.lodestar_server.career.exception;

import com.lodestar.lodestar_server.bookmark.exception.DuplicateBookmarkException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CareerExceptionHandler {

    @ExceptionHandler(DuplicateCareerException.class)
    public ResponseEntity<?> handleDuplicateCareerException(final DuplicateCareerException e) {

        log.error("{}", e.getMessage());

        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedDeleteCareerException.class)
    public ResponseEntity<?> handleUnauthorizedDeleteCareerException(final UnauthorizedDeleteCareerException e) {

        log.error("{}", e.getMessage());

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}

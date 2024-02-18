package com.lodestar.lodestar_server.board.exception;

import com.lodestar.lodestar_server.exception.DuplicateUsernameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class BoardExceptionHandler {

    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<?> handleBoardNotFoundException(final BoardNotFoundException e) {

        log.error("{}", e.getMessage());

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedDeleteException.class)
    public ResponseEntity<?> handleUnauthorizedDeleteException(final UnauthorizedDeleteException e) {

        log.error("{}", e.getMessage());

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UnauthorizedModifyException.class)
    public ResponseEntity<?> handleUnauthorizedModifyException(final UnauthorizedModifyException e) {

        log.error("{}", e.getMessage());

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}

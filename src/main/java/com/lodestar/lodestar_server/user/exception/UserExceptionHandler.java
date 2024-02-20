package com.lodestar.lodestar_server.user.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class UserExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<?> handleDuplicateEmailException(final DuplicateEmailException e) {

        log.error("{}", e.getMessage());

        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }


    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<?> handleDuplicateUsernameException(final DuplicateUsernameException e) {

        log.error("{}", e.getMessage());

        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }



    @ExceptionHandler(NotMatchPasswordException.class)
    public ResponseEntity<?> handleNotMatchPasswordException(final NotMatchPasswordException e) {

        log.error("{}", e.getMessage());

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundByUsernameException.class)
    public ResponseEntity<?> handleUserNotFoundByUsernameException(final UserNotFoundByUsernameException e) {

        log.error("{}", e.getMessage());

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundByEmailException.class)
    public ResponseEntity<?> handleUserNotFoundByEmailException(final UserNotFoundByEmailException e) {

        log.error("{}", e.getMessage());

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundByIdException.class)
    public ResponseEntity<?> handleUserNotFoundByIdException(final UserNotFoundByIdException e) {

        log.error("{}", e.getMessage());

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
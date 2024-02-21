package com.lodestar.lodestar_server.email.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class MailExceptionHandler {

    @ExceptionHandler(NotMatchedAuthenticationKey.class)
    public ResponseEntity<HttpStatus> handleNotInvalidAuthenticationKey(final NotMatchedAuthenticationKey e) {

        log.error("{}", e.getMessage());

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotInvalidAuthenticationTime.class)
    public ResponseEntity<HttpStatus> handleNotInvalidAuthenticationTime(final NotInvalidAuthenticationTime e) {

        log.error("{}", e.getMessage());

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NotMatchedUsernameException.class)
    public ResponseEntity<HttpStatus> handleNotMatchedUsernameException(final NotMatchedUsernameException e) {

        log.error("{}", e.getMessage());

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

package com.lodestar.lodestar_server.user.exception;

import com.lodestar.lodestar_server.exception.ExceptionCode;
import com.lodestar.lodestar_server.exception.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class UserExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ExceptionResponse> handleDuplicateEmailException(final DuplicateEmailException e) {

        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("[duplicate email exception] {}", e.getMessage());

        return new ResponseEntity<>(
                ExceptionResponse.of(exceptionCode),
                exceptionCode.getHttpStatus()
        );
    }


    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<ExceptionResponse> handleDuplicateUsernameException(final DuplicateUsernameException e) {

        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("[duplicate username exception] {}", e.getMessage());

        return new ResponseEntity<>(
                ExceptionResponse.of(exceptionCode),
                exceptionCode.getHttpStatus()
        );
    }


    @ExceptionHandler(NotMatchPasswordException.class)
    public ResponseEntity<ExceptionResponse> handleNotMatchPasswordException(final NotMatchPasswordException e) {

        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("[not match password exception] {}", e.getMessage());

        return new ResponseEntity<>(
                ExceptionResponse.of(exceptionCode),
                exceptionCode.getHttpStatus()
        );
    }

    @ExceptionHandler(UserNotFoundByUsernameException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundByUsernameException(final UserNotFoundByUsernameException e) {

        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("[user not found by username exception] {}", e.getMessage());

        return new ResponseEntity<>(
                ExceptionResponse.of(exceptionCode),
                exceptionCode.getHttpStatus()
        );
    }

    @ExceptionHandler(UserNotFoundByEmailException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundByEmailException(final UserNotFoundByEmailException e) {

        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("[user not found by email exception] {}", e.getMessage());

        return new ResponseEntity<>(
                ExceptionResponse.of(exceptionCode),
                exceptionCode.getHttpStatus()
        );
    }

    @ExceptionHandler(UserNotFoundByIdException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundByIdException(final UserNotFoundByIdException e) {

        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("[user not found by id exception] {}", e.getMessage());

        return new ResponseEntity<>(
                ExceptionResponse.of(exceptionCode),
                exceptionCode.getHttpStatus()
        );
    }
}
package com.lodestar.lodestar_server.email.exception;

import com.lodestar.lodestar_server.exception.ExceptionCode;
import com.lodestar.lodestar_server.exception.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class MailExceptionHandler {

    @ExceptionHandler(NotMatchedAuthenticationKey.class)
    public ResponseEntity<ExceptionResponse> handleNotInvalidAuthenticationKey(final NotMatchedAuthenticationKey e) {

        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("[not matched authentication key exception] {}", e.getMessage());

        return new ResponseEntity<>(
                ExceptionResponse.of(exceptionCode),
                exceptionCode.getHttpStatus()
        );
    }

    @ExceptionHandler(InvalidAuthenticationTime.class)
    public ResponseEntity<ExceptionResponse> handleNotInvalidAuthenticationTime(final InvalidAuthenticationTime e) {

        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("[invalid authentication time] {}", e.getMessage());

        return new ResponseEntity<>(
                ExceptionResponse.of(exceptionCode),
                exceptionCode.getHttpStatus()
        );
    }


    @ExceptionHandler(NotMatchedUsernameException.class)
    public ResponseEntity<ExceptionResponse> handleNotMatchedUsernameException(final NotMatchedUsernameException e) {

        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("[not matched username exception] {}", e.getMessage());

        return new ResponseEntity<>(
                ExceptionResponse.of(exceptionCode),
                exceptionCode.getHttpStatus()
        );
    }
}

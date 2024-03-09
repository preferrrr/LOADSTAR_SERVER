package com.lodestar.lodestar_server.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.lodestar.lodestar_server.exception.GlobalExceptionCode.INVALID_REQUEST_PARAMETER;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({InvalidRequestParameterException.class})
    public ResponseEntity<?> handleInvalidRequestParameterException(final InvalidRequestParameterException e) {

        String msg = e.getNAME() + ": [" + e.getMessage() + "]";
        log.error(msg);

        return new ResponseEntity<>(NO_CONTENT); /**204*/
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity handleBindException(final BindException e) {

        log.error("[bind exception] {}", INVALID_REQUEST_PARAMETER.getMessage());

        return new ResponseEntity(
                ExceptionResponse.of(INVALID_REQUEST_PARAMETER, e.getAllErrors().get(0).getDefaultMessage()),
                INVALID_REQUEST_PARAMETER.getHttpStatus());
    }

}

package com.lodestar.lodestar_server.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity handleBindException(final BindException e) {

        log.error("[bind exception] {}", GlobalExceptionCode.INVALID_REQUEST_PARAMETER.getMessage());

        return new ResponseEntity(
                ExceptionResponse.of(GlobalExceptionCode.INVALID_REQUEST_PARAMETER, e.getAllErrors().get(0).getDefaultMessage()),
                GlobalExceptionCode.INVALID_REQUEST_PARAMETER.getHttpStatus());
    }

}

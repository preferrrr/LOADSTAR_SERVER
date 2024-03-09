package com.lodestar.lodestar_server.career.exception;

import com.lodestar.lodestar_server.bookmark.exception.DuplicateBookmarkException;
import com.lodestar.lodestar_server.exception.ExceptionCode;
import com.lodestar.lodestar_server.exception.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CareerExceptionHandler {

    @ExceptionHandler(DuplicateCareerException.class)
    public ResponseEntity<ExceptionResponse> handleDuplicateCareerException(final DuplicateCareerException e) {

        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("[duplicate career exception] {}", e.getMessage());

        return new ResponseEntity<>(
                ExceptionResponse.of(exceptionCode),
                exceptionCode.getHttpStatus()
        );
    }

    @ExceptionHandler(UnauthorizedDeleteCareerException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedDeleteCareerException(final UnauthorizedDeleteCareerException e) {

        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("[unauthorized delete career exception] {}", e.getMessage());

        return new ResponseEntity<>(
                ExceptionResponse.of(exceptionCode),
                exceptionCode.getHttpStatus()
        );
    }
}

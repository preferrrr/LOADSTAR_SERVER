package com.lodestar.lodestar_server.bookmark.exception;

import com.lodestar.lodestar_server.common.exception.ExceptionCode;
import com.lodestar.lodestar_server.common.exception.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class BookmarkExceptionHandler {

    @ExceptionHandler(DuplicateBookmarkException.class)
    public ResponseEntity<ExceptionResponse> handleDuplicateBookmarkException(final DuplicateBookmarkException e) {

        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("[duplicate bookmark exception] {}", e.getMessage());

        return new ResponseEntity<>(
                ExceptionResponse.of(exceptionCode),
                exceptionCode.getHttpStatus()
        );
    }

    @ExceptionHandler(NotExistsBookmarkException.class)
    public ResponseEntity<ExceptionResponse> handleNotExistBookmarkException(final NotExistsBookmarkException e) {

        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("{}", e.getMessage());

        return new ResponseEntity<>(
                ExceptionResponse.of(exceptionCode),
                exceptionCode.getHttpStatus()
        );
    }
}

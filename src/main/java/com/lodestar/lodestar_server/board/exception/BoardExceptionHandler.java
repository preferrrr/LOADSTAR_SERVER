package com.lodestar.lodestar_server.board.exception;

import com.lodestar.lodestar_server.common.exception.ExceptionCode;
import com.lodestar.lodestar_server.common.exception.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class BoardExceptionHandler {

    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleBoardNotFoundException(final BoardNotFoundException e) {

        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("[board not found exception] {}", e.getMessage());

        return new ResponseEntity<>(
                ExceptionResponse.of(exceptionCode),
                exceptionCode.getHttpStatus());
    }

    @ExceptionHandler(UnauthorizedDeleteException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedDeleteException(final UnauthorizedDeleteException e) {

        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("[unauthorized delete exception] {}", e.getMessage());

        return new ResponseEntity<>(
                ExceptionResponse.of(exceptionCode),
                exceptionCode.getHttpStatus());
    }

    @ExceptionHandler(UnauthorizedModifyException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedModifyException(final UnauthorizedModifyException e) {

        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("[unauthorized modify exception] {}", e.getMessage());

        return new ResponseEntity<>(
                ExceptionResponse.of(exceptionCode),
                exceptionCode.getHttpStatus());
    }
}

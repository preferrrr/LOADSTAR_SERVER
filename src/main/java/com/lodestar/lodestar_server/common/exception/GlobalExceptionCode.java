package com.lodestar.lodestar_server.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum GlobalExceptionCode implements ExceptionCode {

    INVALID_REQUEST_PARAMETER(BAD_REQUEST, "유효하지 않은 파라미터 입니다.");

    private final HttpStatus httpStatus;
    private final String message;

}

package com.lodestar.lodestar_server.email.exception;

import com.lodestar.lodestar_server.common.exception.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum MailExceptionCode implements ExceptionCode {

    INVALID_AUTHENTICATION_TIME(BAD_REQUEST, "유효 시간을 초과했습니다."),
    NOT_MATCHED_AUTHENTICATION_KEY(BAD_REQUEST, "인증키가 틀렸습니다."),
    NOT_MATCHED_USERNAME(BAD_REQUEST, "잘못된 아이디입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}

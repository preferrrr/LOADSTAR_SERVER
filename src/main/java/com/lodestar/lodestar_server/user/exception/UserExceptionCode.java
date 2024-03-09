package com.lodestar.lodestar_server.user.exception;

import com.lodestar.lodestar_server.exception.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@Getter
public enum UserExceptionCode implements ExceptionCode {

    DUPLICATE_EMAIL(CONFLICT, "이미 가입한 이메일입니다."),
    DUPLICATE_USERNAME(CONFLICT, "이미 가입한 아이디입니다."),
    NOT_MATCH_PASSWORD(BAD_REQUEST, "비밀번호가 틀렸습니다."),
    USER_NOT_FOUND_BY_EMAIL(NOT_FOUND, "존재하지 않는 유저입니다."),
    USER_NOT_FOUND_BY_ID(NOT_FOUND, "존재하지 않는 유저입니다."),
    USER_NOT_FOUND_BY_USERNAME(NOT_FOUND, "존재하지 않는 유저입니다.");


    private final HttpStatus httpStatus;
    private final String message;
}

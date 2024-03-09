package com.lodestar.lodestar_server.career.exception;

import com.lodestar.lodestar_server.common.exception.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CareerExceptionCode implements ExceptionCode {

    DUPLICATE_CAREER(HttpStatus.CONFLICT, "이미 커리어를 등록했습니다."),
    UNAUTHORIZED_MODIFY_CAREER(HttpStatus.FORBIDDEN, "커리어를 수정할 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}

package com.lodestar.lodestar_server.career.exception;

import com.lodestar.lodestar_server.common.exception.ExceptionCode;
import lombok.Getter;

import static com.lodestar.lodestar_server.career.exception.CareerExceptionCode.*;

public class UnauthorizedDeleteCareerException extends RuntimeException {
    @Getter
    private final ExceptionCode exceptionCode;

    public UnauthorizedDeleteCareerException() {
        super(UNAUTHORIZED_MODIFY_CAREER.getMessage());
        this.exceptionCode = UNAUTHORIZED_MODIFY_CAREER;
    }
}

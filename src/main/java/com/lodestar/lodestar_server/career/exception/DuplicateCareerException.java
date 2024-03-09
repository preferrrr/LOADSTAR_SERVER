package com.lodestar.lodestar_server.career.exception;

import com.lodestar.lodestar_server.common.exception.ExceptionCode;
import lombok.Getter;

import static com.lodestar.lodestar_server.career.exception.CareerExceptionCode.*;

public class DuplicateCareerException extends RuntimeException{
    @Getter
    private final ExceptionCode exceptionCode;

    public DuplicateCareerException() {
        super(DUPLICATE_CAREER.getMessage());
        this.exceptionCode = DUPLICATE_CAREER;
    }
}

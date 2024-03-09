package com.lodestar.lodestar_server.common.exception;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExceptionResponse {

    private HttpStatus status;
    private String message;
    private LocalDateTime time;

    @Builder
    private ExceptionResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.time = LocalDateTime.now();
    }

    public static ExceptionResponse of(ExceptionCode exceptionCode, String message) {
        return ExceptionResponse.builder()
                .status(exceptionCode.getHttpStatus())
                .message(message)
                .build();
    }

    public static ExceptionResponse of(ExceptionCode exceptionCode) {
        return ExceptionResponse.builder()
                .status(exceptionCode.getHttpStatus())
                .message(exceptionCode.getMessage())
                .build();
    }
}

package com.lodestar.lodestar_server.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class BaseResponse {

    private HttpStatus status;
    private LocalDateTime time;

    protected BaseResponse(HttpStatus status) {
        this.status = status;
        this.time = LocalDateTime.now();
    }

    public static BaseResponse of(HttpStatus status) {
        return new BaseResponse(status);
    }
}

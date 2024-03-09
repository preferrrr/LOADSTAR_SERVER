package com.lodestar.lodestar_server.common.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionCode {

    HttpStatus getHttpStatus();
    String getMessage();

}

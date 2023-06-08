package com.lodestar.lodestar_server.exception;

import lombok.Getter;

public class AuthFailException extends RuntimeException{
    @Getter
    private final String NAME;

    public AuthFailException(String msg) {
        super(msg);
        NAME = "AuthFailException";
    }
}

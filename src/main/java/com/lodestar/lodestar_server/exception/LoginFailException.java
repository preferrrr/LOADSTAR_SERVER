package com.lodestar.lodestar_server.exception;


import lombok.Getter;

public class LoginFailException extends RuntimeException{
    @Getter
    private final String NAME;

    public LoginFailException(String msg) {
        super(msg);
        NAME = "DuplicateEmailException";
    }
}

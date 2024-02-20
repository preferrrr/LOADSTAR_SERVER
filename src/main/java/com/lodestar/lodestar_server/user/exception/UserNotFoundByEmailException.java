package com.lodestar.lodestar_server.user.exception;

import lombok.Getter;

public class UserNotFoundByEmailException extends RuntimeException{
    @Getter
    private final String NAME;

    public UserNotFoundByEmailException() {
        NAME = "UserNotFoundByEmailException";
    }
}

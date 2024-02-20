package com.lodestar.lodestar_server.user.exception;

import lombok.Getter;

public class UserNotFoundByUsernameException extends RuntimeException{
    @Getter
    private final String NAME;

    public UserNotFoundByUsernameException() {
        NAME = "UserNotFoundByUsernameException";
    }
}

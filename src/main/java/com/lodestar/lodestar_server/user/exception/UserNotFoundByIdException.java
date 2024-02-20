package com.lodestar.lodestar_server.user.exception;

import lombok.Getter;

public class UserNotFoundByIdException extends RuntimeException{
    @Getter
    private final String NAME;

    public UserNotFoundByIdException() {
        NAME = "UserNotFoundByIdException";
    }
}

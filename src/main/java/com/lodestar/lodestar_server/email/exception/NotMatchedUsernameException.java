package com.lodestar.lodestar_server.email.exception;

import lombok.Getter;

public class NotMatchedUsernameException extends RuntimeException {
    @Getter
    private final String NAME;

    public NotMatchedUsernameException() {
        NAME = "NotMatchedUsernameException";
    }
}

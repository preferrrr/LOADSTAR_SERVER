package com.lodestar.lodestar_server.email.exception;

import lombok.Getter;

public class NotInvalidAuthenticationTime extends RuntimeException {
    @Getter
    private final String NAME;

    public NotInvalidAuthenticationTime() {
        NAME = "NotInvalidAuthenticationTime";
    }
}

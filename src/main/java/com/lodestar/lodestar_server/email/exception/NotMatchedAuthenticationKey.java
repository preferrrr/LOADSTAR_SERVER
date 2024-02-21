package com.lodestar.lodestar_server.email.exception;

import lombok.Getter;

public class NotMatchedAuthenticationKey extends RuntimeException{
    @Getter
    private final String NAME;

    public NotMatchedAuthenticationKey() {
        NAME = "NotMatchedAuthenticationKey";
    }
}

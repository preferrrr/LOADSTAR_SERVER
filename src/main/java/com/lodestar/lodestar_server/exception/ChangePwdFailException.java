package com.lodestar.lodestar_server.exception;

import lombok.Getter;

public class ChangePwdFailException extends RuntimeException{
    @Getter
    private final String NAME;

    public ChangePwdFailException(String msg) {
        super(msg);
        NAME = "ChangePwdFailException";
    }
}

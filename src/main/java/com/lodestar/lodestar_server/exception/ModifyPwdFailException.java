package com.lodestar.lodestar_server.exception;

import lombok.Getter;

public class ModifyPwdFailException extends RuntimeException{
    @Getter
    private final String NAME;

    public ModifyPwdFailException(String msg) {
        super(msg);
        NAME = "ChangePwdFailException";
    }
}

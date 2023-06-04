package com.lodestar.lodestar_server.exception;

import lombok.Getter;

public class SendMailFailException extends RuntimeException{
    @Getter
    private final String NAME;

    public SendMailFailException(String msg) {
        super(msg);
        NAME = "SendMailFailException";
    }
}

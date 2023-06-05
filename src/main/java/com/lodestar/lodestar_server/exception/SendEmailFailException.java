package com.lodestar.lodestar_server.exception;

import lombok.Getter;

public class SendEmailFailException extends RuntimeException{
    @Getter
    private final String NAME;

    public SendEmailFailException(String msg) {
        super(msg);
        NAME = "SendMailFailException";
    }
}

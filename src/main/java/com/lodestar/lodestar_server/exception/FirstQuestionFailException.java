package com.lodestar.lodestar_server.exception;

import lombok.Getter;

public class FirstQuestionFailException extends RuntimeException {
    @Getter
    private final String NAME;

    public FirstQuestionFailException(String msg) {
        super(msg);
        NAME = "FirstQuestionFailException";
    }
}

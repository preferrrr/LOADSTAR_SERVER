package com.lodestar.lodestar_server.comment.exception;

import lombok.Getter;

public class CommentNotFoundException extends RuntimeException{
    @Getter
    private final String NAME;

    public CommentNotFoundException() {
        NAME = "CommentNotFoundException";
    }
}

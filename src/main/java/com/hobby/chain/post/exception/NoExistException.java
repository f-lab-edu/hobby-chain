package com.hobby.chain.post.exception;

public class NoExistException extends RuntimeException{
    public NoExistException() {
        super();
    }

    public NoExistException(String message) {
        super(message);
    }
}

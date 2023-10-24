package com.hobby.chain.post.exception;

public class NoExistsPost extends RuntimeException{
    public NoExistsPost() {
        super();
    }

    public NoExistsPost(String message) {
        super(message);
    }
}

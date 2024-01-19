package com.hobby.chain.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Not Authorized")
public class ForbiddenException extends RuntimeException{
    public ForbiddenException() {
        super();
    }

    public ForbiddenException(String message) {
        super(message);
    }
}

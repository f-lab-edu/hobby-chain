package com.hobby.chain.like.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Already Like Post")
public class AlreadyLikeException extends RuntimeException{
}

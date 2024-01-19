package com.hobby.chain.follow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Not Following")
public class NotFollowingUserException extends RuntimeException{
}

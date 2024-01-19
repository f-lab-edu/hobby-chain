package com.hobby.chain.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Not Exist UserId")
public class NotExistUserException extends RuntimeException{
}

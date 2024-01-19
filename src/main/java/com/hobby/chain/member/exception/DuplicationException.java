package com.hobby.chain.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Already Exists")
public class DuplicationException extends RuntimeException{
}

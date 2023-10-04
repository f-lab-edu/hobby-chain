package com.hobby.chain.post.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FileUploadFailException extends RuntimeException{
    public FileUploadFailException() {
        super();
    }

    public FileUploadFailException(String message){
        super(message);
    }
}

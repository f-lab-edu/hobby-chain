package com.hobby.chain.post.exception;


public class FileUploadFailException extends RuntimeException{
    public FileUploadFailException() {
        super();
    }

    public FileUploadFailException(String message){
        super(message);
    }
}

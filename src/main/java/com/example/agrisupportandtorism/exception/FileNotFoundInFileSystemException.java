package com.example.agrisupportandtorism.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class FileNotFoundInFileSystemException extends RuntimeException{
    public FileNotFoundInFileSystemException(String message){
        super(message);
    }
    public FileNotFoundInFileSystemException(String message, Throwable throwable){
        super(message, throwable);
    }
}

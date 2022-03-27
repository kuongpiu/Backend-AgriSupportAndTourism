package com.example.agrisupportandtorism.exception;

public class CreateDocumentException extends RuntimeException{
    public CreateDocumentException(String message){
        super(message);
    }
    public CreateDocumentException(String message, Throwable throwable){
        super(message, throwable);
    }
}

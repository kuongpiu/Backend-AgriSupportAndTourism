package com.example.agrisupportandtorism.exception;

public class ResourceConflict extends RuntimeException{
    public ResourceConflict(String message){
        super(message);
    }
    public ResourceConflict(String message, Throwable throwable){
        super(message, throwable);
    }
}

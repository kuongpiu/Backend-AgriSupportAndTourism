package com.example.agrisupportandtorism.exception.handler;

import com.example.agrisupportandtorism.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Handler {
    @ExceptionHandler({CreateDocumentException.class})
    ResponseEntity<String> handleCreateDocumentException(CreateDocumentException e){
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal Server Error!");
    }

    @ExceptionHandler({ResourceNotFoundException.class, FileNotFoundInFileSystemException.class})
    ResponseEntity<String> handleResourceNotFound(Exception e){
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Resource Not Found!");
    }

    @ExceptionHandler({ResourceConflict.class})
    ResponseEntity<String> handleResourceConflict(ResourceConflict e){
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Resource conflict");
    }

    @ExceptionHandler({PermissionException.class})
    ResponseEntity<String> handlePermissionException(PermissionException e){
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("Your permission is not granted to do this");
    }
}

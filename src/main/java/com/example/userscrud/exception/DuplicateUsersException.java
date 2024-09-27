package com.example.userscrud.exception;

public class DuplicateUsersException extends RuntimeException{
    public DuplicateUsersException(String message) {
        super(message);
    }
}

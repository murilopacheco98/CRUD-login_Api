package com.growdev.murilo.recados.exceptions.customExceptions;

// 404 - Not Found
public class NotFoundException extends IllegalArgumentException{
    public NotFoundException(String message) {
        super(message);
    }
}

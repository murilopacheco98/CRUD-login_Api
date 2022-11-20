package com.growdev.murilo.recados.exceptions.customExceptions;

// 400 - bad request
public class BadRequestException extends IllegalArgumentException {

    public BadRequestException(String message) {
        super(message);
    }
}

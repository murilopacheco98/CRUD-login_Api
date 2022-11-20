package com.growdev.murilo.recados.exceptions.customExceptions;

// 500 - internal error
public class InternalServerErrorException extends IllegalArgumentException{
    public InternalServerErrorException(String message) {
        super(message);
    }
}

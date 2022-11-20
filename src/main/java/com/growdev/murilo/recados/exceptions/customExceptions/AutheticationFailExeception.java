package com.growdev.murilo.recados.exceptions.customExceptions;

// 401 - unauthorized
public class AutheticationFailExeception extends IllegalArgumentException {
    public AutheticationFailExeception(String message) {
        super(message);
    }
}

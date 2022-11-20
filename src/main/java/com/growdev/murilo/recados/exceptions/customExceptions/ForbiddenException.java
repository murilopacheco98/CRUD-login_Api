package com.growdev.murilo.recados.exceptions.customExceptions;

// 403 - forbidden
public class ForbiddenException extends IllegalArgumentException {
  public ForbiddenException(String message) {
    super(message);
  }
}

package com.vadmack.petter.exception;

public class ValidationException extends RuntimeException {
  public ValidationException(String message){
    super(message);
  }
}
package com.vadmack.petter.app.exception.handler;

import com.vadmack.petter.app.exception.NotFoundException;
import com.vadmack.petter.app.exception.UnauthorizedException;
import com.vadmack.petter.app.exception.ValidationException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler({NotFoundException.class})
  public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({ValidationException.class})
  public ResponseEntity<String> handleValidationException(ValidationException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({UnauthorizedException.class})
  public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
          MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({FileSizeLimitExceededException.class})
  public ResponseEntity<String> handleFileSizeLimitExceededException(FileSizeLimitExceededException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }
}

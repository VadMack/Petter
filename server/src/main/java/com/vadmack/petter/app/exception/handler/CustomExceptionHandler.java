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
  public ResponseEntity<ExceptionResponseWrapper> handleNotFoundException(NotFoundException ex) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    return new ResponseEntity<>(new ExceptionResponseWrapper(status.value(), ex.getMessage()), status);
  }

  @ExceptionHandler({ValidationException.class})
  public ResponseEntity<ExceptionResponseWrapper> handleValidationException(ValidationException ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    return new ResponseEntity<>(new ExceptionResponseWrapper(status.value(), ex.getMessage()), status);
  }

  @ExceptionHandler({UnauthorizedException.class})
  public ResponseEntity<ExceptionResponseWrapper> handleUnauthorizedException(UnauthorizedException ex) {
    HttpStatus status = HttpStatus.UNAUTHORIZED;
    return new ResponseEntity<>(new ExceptionResponseWrapper(status.value(), ex.getMessage()), status);
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<ExceptionResponseWrapper> handleMethodArgumentNotValidException(
          MethodArgumentNotValidException ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return new ResponseEntity<>(new ExceptionResponseWrapper(status.value(), errors.toString()), status);
  }

  @ExceptionHandler({FileSizeLimitExceededException.class})
  public ResponseEntity<ExceptionResponseWrapper> handleFileSizeLimitExceededException(
          FileSizeLimitExceededException ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    return new ResponseEntity<>(new ExceptionResponseWrapper(status.value(), ex.getMessage()), status);
  }
}

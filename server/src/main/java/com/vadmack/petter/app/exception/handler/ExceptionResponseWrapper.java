package com.vadmack.petter.app.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponseWrapper {
  private int status;
  private String error;
}

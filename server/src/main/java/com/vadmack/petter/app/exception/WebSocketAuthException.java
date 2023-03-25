package com.vadmack.petter.app.exception;

import org.springframework.security.core.AuthenticationException;

public class WebSocketAuthException extends AuthenticationException {
  public WebSocketAuthException(String msg) {
    super(msg);
  }
}

package com.vadmack.petter.security.event;

public class OnPasswordResetEvent extends ConfirmationCodeEvent {
  public OnPasswordResetEvent(Object source, String recipientAddress, short confirmationCode) {
    super(source, recipientAddress, confirmationCode);
  }
}

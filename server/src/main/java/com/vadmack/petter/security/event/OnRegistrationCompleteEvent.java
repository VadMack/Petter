package com.vadmack.petter.security.event;

public class OnRegistrationCompleteEvent extends ConfirmationCodeEvent {
  public OnRegistrationCompleteEvent(Object source, String recipientAddress, short confirmationCode) {
    super(source, recipientAddress, confirmationCode);
  }
}

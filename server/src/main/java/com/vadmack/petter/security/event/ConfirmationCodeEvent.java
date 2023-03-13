package com.vadmack.petter.security.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public abstract class ConfirmationCodeEvent extends ApplicationEvent {
  private String recipientAddress;
  private short confirmationCode;

  protected ConfirmationCodeEvent(Object source, String recipientAddress, short confirmationCode) {
    super(source);
    this.recipientAddress = recipientAddress;
    this.confirmationCode = confirmationCode;
  }
}

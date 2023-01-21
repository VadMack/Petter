package com.vadmack.petter.security.listener;

import com.vadmack.petter.mail.MailService;
import com.vadmack.petter.security.event.OnRegistrationCompleteEvent;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

  private static final String REGISTRATION_EMAIL_SUBJECT = "Petter registration";
  private static final String REGISTRATION_EMAIL_TEMPLATE = "Confirm registration by entering the following numbers" +
          " in the mobile application: ";

  private final MailService emailService;

  @Override
  public void onApplicationEvent(@NotNull OnRegistrationCompleteEvent event) {
    emailService.send(event.getRecipientAddress(), REGISTRATION_EMAIL_SUBJECT,
            REGISTRATION_EMAIL_TEMPLATE + event.getConfirmationCode());
  }
}

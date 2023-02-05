package com.vadmack.petter.security.listener;

import com.vadmack.petter.mail.MailService;
import com.vadmack.petter.security.event.OnPasswordResetEvent;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordResetListener implements ApplicationListener<OnPasswordResetEvent> {

  private static final String PASSWORD_RESET_EMAIL_SUBJECT = "Petter password reset";
  private static final String PASSWORD_RESET_EMAIL_TEMPLATE =
          "Confirm password reset by entering the following numbers in the mobile application: ";

  private final MailService emailService;

  @Override
  public void onApplicationEvent(@NotNull OnPasswordResetEvent event) {
    emailService.send(event.getRecipientAddress(), PASSWORD_RESET_EMAIL_SUBJECT,
            PASSWORD_RESET_EMAIL_TEMPLATE + event.getConfirmationCode());
  }
}

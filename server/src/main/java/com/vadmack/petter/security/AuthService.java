package com.vadmack.petter.security;

import com.vadmack.petter.security.confirmationcode.ConfirmationCodeService;
import com.vadmack.petter.security.confirmationcode.ConfirmationCodeType;
import com.vadmack.petter.security.dto.ConfirmationCodeRequest;
import com.vadmack.petter.security.dto.PasswordResetConfirmationRequest;
import com.vadmack.petter.security.event.OnPasswordResetEvent;
import com.vadmack.petter.security.event.OnRegistrationCompleteEvent;
import com.vadmack.petter.user.User;
import com.vadmack.petter.user.UserService;
import com.vadmack.petter.user.dto.UserCreateDto;
import com.vadmack.petter.user.dto.UserGetDto;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

  private final UserService userService;
  private final ConfirmationCodeService confirmationCodeService;

  private final ApplicationEventPublisher eventPublisher;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public @NotNull UserGetDto register(@NotNull UserCreateDto dto) {
    User createdUser = userService.create(dto);
    short code = confirmationCodeService.create(createdUser.getId(), ConfirmationCodeType.REGISTRATION);
    eventPublisher.publishEvent(new OnRegistrationCompleteEvent(this, dto.getEmail(), code));
    return userService.entityToDto(createdUser);
  }

  @Transactional
  public void registrationConfirm(@NotNull ConfirmationCodeRequest request) {
    confirmationCodeService.validateCode(request.getCode(), request.getUserId(), ConfirmationCodeType.REGISTRATION);
    User user = userService.getById(request.getUserId());
    user.setEnabled(true);
    userService.save(user);
    confirmationCodeService.deleteByCodeAndUserIdAndType(request.getCode(), request.getUserId(),
            ConfirmationCodeType.REGISTRATION);
  }

  public @NotNull UserGetDto resetPassword(@NotNull String email) {
    User user = userService.getByEmail(email);
    short code = confirmationCodeService.create(user.getId(), ConfirmationCodeType.PASSWORD_RESET);
    eventPublisher.publishEvent(new OnPasswordResetEvent(this, email, code));
    return userService.entityToDto(user);
  }

  public void resetPasswordConfirm(@NotNull PasswordResetConfirmationRequest request) {
    confirmationCodeService.validateCode(request.getCode(), request.getUserId(), ConfirmationCodeType.PASSWORD_RESET);
    User user = userService.getById(request.getUserId());
    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    userService.save(user);
    confirmationCodeService.deleteByCodeAndUserIdAndType(request.getCode(), request.getUserId(),
            ConfirmationCodeType.PASSWORD_RESET);
  }

}

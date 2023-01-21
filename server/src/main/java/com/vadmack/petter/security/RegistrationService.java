package com.vadmack.petter.security;

import com.vadmack.petter.security.confirmationcode.ConfirmationCodeService;
import com.vadmack.petter.security.confirmationcode.ConfirmationCodeType;
import com.vadmack.petter.security.dto.RegistrationConfirmRequest;
import com.vadmack.petter.security.event.OnRegistrationCompleteEvent;
import com.vadmack.petter.user.User;
import com.vadmack.petter.user.UserService;
import com.vadmack.petter.user.dto.UserCreateDto;
import com.vadmack.petter.user.dto.UserGetDto;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RegistrationService {

  private final UserService userService;
  private final ConfirmationCodeService confirmationCodeService;

  private final ApplicationEventPublisher eventPublisher;

  @Transactional
  public @NotNull UserGetDto register(@NotNull UserCreateDto dto) {
    User createdUser = userService.create(dto);
    short code = confirmationCodeService.create(createdUser.getId(), ConfirmationCodeType.REGISTRATION);
    eventPublisher.publishEvent(new OnRegistrationCompleteEvent(this, dto.getEmail(), code));
    return userService.entityToDto(createdUser);
  }

  @Transactional
  public void registrationConfirm(@NotNull RegistrationConfirmRequest request) {
    confirmationCodeService.validateCode(request.getCode(), request.getUserId(), ConfirmationCodeType.REGISTRATION);
    User user = userService.getById(request.getUserId());
    user.setEnabled(true);
    userService.save(user);
    confirmationCodeService.deleteByCodeAndUserIdAndType(request.getCode(), request.getUserId(),
            ConfirmationCodeType.REGISTRATION);
  }

}

package com.vadmack.petter.security;

import com.vadmack.petter.app.exception.UnauthorizedException;
import com.vadmack.petter.security.confirmationcode.ConfirmationCode;
import com.vadmack.petter.security.confirmationcode.ConfirmationCodeService;
import com.vadmack.petter.security.confirmationcode.ConfirmationCodeType;
import com.vadmack.petter.security.dto.request.ConfirmationCodeRequest;
import com.vadmack.petter.security.dto.request.PasswordResetConfirmationRequest;
import com.vadmack.petter.security.dto.response.LoginResponse;
import com.vadmack.petter.security.event.OnPasswordResetEvent;
import com.vadmack.petter.security.event.OnRegistrationCompleteEvent;
import com.vadmack.petter.security.token.TokenService;
import com.vadmack.petter.user.User;
import com.vadmack.petter.user.UserService;
import com.vadmack.petter.user.dto.UserCreateDto;
import com.vadmack.petter.user.dto.UserGetDto;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {

  private final JwtTokenUtil jwtTokenUtil;
  private final UserService userService;
  private final ConfirmationCodeService confirmationCodeService;
  private final TokenService tokenService;

  private final AuthenticationManager authenticationManager;
  private final ApplicationEventPublisher eventPublisher;
  private final PasswordEncoder passwordEncoder;
  private final ModelMapper modelMapper;

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

  @Transactional
  public @NotNull UserGetDto resendRegistrationConfirmationCode(@NotNull String email) {
    return resendConfirmationCode(email, ConfirmationCodeType.REGISTRATION);
  }

  public void declineEmailConfirmation(@NotNull String email) {
    userService.deleteByEmailIfNotActivated(email);
  }

  @Transactional
  public @NotNull UserGetDto resetPassword(@NotNull String email) {
    User user = userService.getByEmail(email);
    short code = confirmationCodeService.create(user.getId(), ConfirmationCodeType.PASSWORD_RESET);
    eventPublisher.publishEvent(new OnPasswordResetEvent(this, email, code));
    return userService.entityToDto(user);
  }

  @Transactional
  public void resetPasswordConfirm(@NotNull PasswordResetConfirmationRequest request) {
    confirmationCodeService.validateCode(request.getCode(), request.getUserId(), ConfirmationCodeType.PASSWORD_RESET);
    User user = userService.getById(request.getUserId());
    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    userService.save(user);
    confirmationCodeService.deleteByCodeAndUserIdAndType(request.getCode(), request.getUserId(),
            ConfirmationCodeType.PASSWORD_RESET);
  }

  @Transactional
  public @NotNull UserGetDto resendResetPasswordConfirmationCode(@NotNull String email) {
    return resendConfirmationCode(email, ConfirmationCodeType.PASSWORD_RESET);
  }

  private @NotNull UserGetDto resendConfirmationCode(@NotNull String email, ConfirmationCodeType type) {
    User user = userService.getByEmail(email);
    Optional<ConfirmationCode> existedConfirmationCode = confirmationCodeService
            .findByUserIdAndType(user.getId(), type);
    short code = existedConfirmationCode.map(ConfirmationCode::getCode)
            .orElseGet(() -> confirmationCodeService.create(user.getId(), type));
    eventPublisher.publishEvent(new OnRegistrationCompleteEvent(this, email, code));
    return userService.entityToDto(user);
  }

  public @NotNull LoginResponse login(@NotNull String username, @NotNull String password) {
    Authentication authenticate;
    try {
      authenticate = authenticationManager
              .authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (AuthenticationException e) {
      throw new UnauthorizedException(e.getMessage());
    }
    User user = (User) authenticate.getPrincipal();

    UserGetDto userDto = modelMapper.map(user, UserGetDto.class);
    String refreshToken = tokenService.createRefreshToken(user.getId());
    String jwtToken = jwtTokenUtil.generateAccessToken(user);
    return new LoginResponse(userDto, refreshToken, jwtToken);
  }

  @Transactional
  public LoginResponse refreshToken(String tokenValue) {
    String userId = tokenService.validate(tokenValue);
    tokenService.deleteByValue(tokenValue);
    User user = userService.getById(userId);
    String refreshToken = tokenService.createRefreshToken(userId);
    String jwtToken = jwtTokenUtil.generateAccessToken(user);
    return new LoginResponse(modelMapper.map(user, UserGetDto.class), refreshToken, jwtToken);
  }

}

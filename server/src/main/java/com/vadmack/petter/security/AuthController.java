package com.vadmack.petter.security;

import com.vadmack.petter.security.dto.request.*;
import com.vadmack.petter.security.dto.response.LoginResponse;
import com.vadmack.petter.user.dto.UserCreateDto;
import com.vadmack.petter.user.dto.UserGetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("api")
@RestController
public class AuthController {
  private final AuthService authService;

  @PostMapping("/registration")
  public ResponseEntity<UserGetDto> register(@Valid @RequestBody UserCreateDto dto) {
    UserGetDto createdUser = authService.register(dto);
    return ResponseEntity.ok(createdUser);
  }

  @PostMapping("/registration/confirm")
  public ResponseEntity<?> registrationConfirm(@Valid @RequestBody ConfirmationCodeRequest request) {
    authService.registrationConfirm(request);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/registration/resend-confirmation-code")
  public ResponseEntity<UserGetDto> resendRegistrationConfirmationCode(@Valid @RequestBody EmailWrapper request) {
    UserGetDto user = authService.resendRegistrationConfirmationCode(request.getEmail());
    return ResponseEntity.ok(user);
  }

  @PostMapping("/registration/decline-confirmation")
  public ResponseEntity<UserGetDto> declineEmailConfirmation(@Valid @RequestBody EmailWrapper request) {
    authService.declineEmailConfirmation(request.getEmail());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/password-reset")
  public ResponseEntity<?> resetPassword(@Valid @RequestBody EmailWrapper request) {
    UserGetDto user = authService.resetPassword(request.getEmail());
    return ResponseEntity.ok(user);
  }

  @PostMapping("/password-reset/confirm")
  public ResponseEntity<?> resetPasswordConfirm(@Valid @RequestBody PasswordResetConfirmationRequest request) {
    authService.resetPasswordConfirm(request);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/password-reset/resend-confirmation-code")
  public ResponseEntity<UserGetDto> resendResetPasswordConfirmationCode(@Valid @RequestBody EmailWrapper request) {
    UserGetDto user = authService.resendResetPasswordConfirmationCode(request.getEmail());
    return ResponseEntity.ok(user);
  }

  @PostMapping("/auth")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody AuthRequest request) {
    LoginResponse loginResponse = authService.login(request);
    return ResponseEntity.ok()
            .header(
                    HttpHeaders.AUTHORIZATION,
                    loginResponse.jwtToken()
            )
            .body(loginResponse);
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<LoginResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
    LoginResponse loginResponse = authService.refreshToken(request);
    return ResponseEntity.ok()
            .header(
                    HttpHeaders.AUTHORIZATION,
                    loginResponse.jwtToken()
            )
            .body(loginResponse);
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                              @Valid @RequestBody LogoutRequest request) {
    authService.logout(authHeader.split(" ")[1].trim(), request.getDeviceToken());
    return new ResponseEntity<>(HttpStatus.OK);
  }
}

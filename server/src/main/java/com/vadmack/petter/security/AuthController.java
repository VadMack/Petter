package com.vadmack.petter.security;

import com.vadmack.petter.security.dto.request.*;
import com.vadmack.petter.security.dto.response.LoginResponse;
import com.vadmack.petter.user.dto.UserCreateDto;
import com.vadmack.petter.user.dto.UserGetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("api")
@RestController
public class AuthController {
  private final AuthService authService;

  @PostMapping("/registration")
  public ResponseEntity<UserGetDto> register(@RequestBody UserCreateDto dto) {
    UserGetDto createdUser = authService.register(dto);
    return ResponseEntity.ok(createdUser);
  }

  @PostMapping("/registration-confirm")
  public ResponseEntity<?> registrationConfirm(@RequestBody ConfirmationCodeRequest request) {
    authService.registrationConfirm(request);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/password-reset")
  public ResponseEntity<UserGetDto> resetPassword(@RequestBody PasswordResetRequest request) {
    UserGetDto user = authService.resetPassword(request.getEmail());
    return ResponseEntity.ok(user);
  }

  @PostMapping("/password-reset-confirm")
  public ResponseEntity<?> resetPasswordConfirm(@RequestBody PasswordResetConfirmationRequest request) {
    authService.resetPasswordConfirm(request);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/auth")
  public ResponseEntity<LoginResponse> login(@RequestBody @Valid AuthRequest request) {
    LoginResponse loginResponse = authService.login(request.getUsername(), request.getPassword());
    return ResponseEntity.ok()
            .header(
                    HttpHeaders.AUTHORIZATION,
                    loginResponse.jwtToken()
            )
            .body(loginResponse);
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<LoginResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
    LoginResponse loginResponse = authService.refreshToken(request.getToken());
    return ResponseEntity.ok()
            .header(
                    HttpHeaders.AUTHORIZATION,
                    loginResponse.jwtToken()
            )
            .body(loginResponse);
  }
}

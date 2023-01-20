package com.vadmack.petter.security;

import com.vadmack.petter.app.exception.UnauthorizedException;
import com.vadmack.petter.security.dto.AuthRequest;
import com.vadmack.petter.security.dto.RegistrationConfirmRequest;
import com.vadmack.petter.user.User;
import com.vadmack.petter.user.dto.UserCreateDto;
import com.vadmack.petter.user.dto.UserGetDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("api")
@RestController
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  private final ModelMapper modelMapper;
  private final RegistrationService registrationService;

  @PostMapping("/registration")
  public ResponseEntity<UserGetDto> register(@RequestBody UserCreateDto dto) {
    UserGetDto createdUser = registrationService.register(dto);
    return ResponseEntity.ok(createdUser);
  }

  @PostMapping("/registration-confirm")
  public ResponseEntity<?> registrationConfirm(@RequestBody RegistrationConfirmRequest request) {
    registrationService.registrationConfirm(request);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/auth")
  public ResponseEntity<UserGetDto> login(@RequestBody @Valid AuthRequest request) {
    Authentication authenticate;

    try {
      authenticate = authenticationManager
              .authenticate(
                      new UsernamePasswordAuthenticationToken(
                              request.getUsername(), request.getPassword()
                      )
              );
    } catch (AuthenticationException e) {
      throw new UnauthorizedException("Bad credentials");
    }

    User user = (User) authenticate.getPrincipal();
    UserGetDto userDto = modelMapper.map(user, UserGetDto.class);
    return ResponseEntity.ok()
            .header(
                    HttpHeaders.AUTHORIZATION,
                    jwtTokenUtil.generateAccessToken(user)
            )
            .body(userDto);
  }
}

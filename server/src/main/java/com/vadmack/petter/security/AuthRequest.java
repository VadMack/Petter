package com.vadmack.petter.security;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class AuthRequest {

  @NotBlank
  private String username;
  @NotBlank
  private String password;

}


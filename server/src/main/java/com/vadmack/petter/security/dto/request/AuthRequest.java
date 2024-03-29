package com.vadmack.petter.security.dto.request;

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
  /**
   * A token identifying the device. Used for notifications via Firebase
   */
  @NotBlank
  private String deviceToken;
}


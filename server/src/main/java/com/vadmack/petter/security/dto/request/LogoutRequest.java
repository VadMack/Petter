package com.vadmack.petter.security.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class LogoutRequest {
  /**
   * A token identifying the device. Used for notifications via Firebase
   */
  @NotBlank
  private String deviceToken;
}

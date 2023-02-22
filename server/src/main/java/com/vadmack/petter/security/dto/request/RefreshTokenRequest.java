package com.vadmack.petter.security.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RefreshTokenRequest {
  @NotBlank
  private String token;
}

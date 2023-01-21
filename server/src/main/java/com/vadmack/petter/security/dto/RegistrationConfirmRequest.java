package com.vadmack.petter.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class RegistrationConfirmRequest {
  @Min(1000)
  @Max(9999)
  private short code;
  @NotBlank
  private String userId;
}

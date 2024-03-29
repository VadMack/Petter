package com.vadmack.petter.security.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class EmailWrapper {
  @Email
  @NotBlank
  private String email;
}

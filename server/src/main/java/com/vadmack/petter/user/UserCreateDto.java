package com.vadmack.petter.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserCreateDto {
  @Email
  private String email;
  @NotBlank
  private String username;
  @NotBlank
  private String password;
  @NotBlank
  private String displayName;
}

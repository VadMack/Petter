package com.vadmack.petter.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetConfirmationRequest extends ConfirmationCodeRequest {
  private String newPassword;
}

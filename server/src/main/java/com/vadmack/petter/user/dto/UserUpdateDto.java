package com.vadmack.petter.user.dto;

import com.vadmack.petter.app.model.ModelUpdateDto;
import com.vadmack.petter.user.Address;
import lombok.Data;

@Data
public class UserUpdateDto implements ModelUpdateDto {
  private String displayName;
  private String phoneNumber;
  private Address address;
}

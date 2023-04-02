package com.vadmack.petter.user.dto;

import com.vadmack.petter.app.model.Address;
import lombok.Data;

@Data
public class UserGetDto {
  private String id;
  private String email;
  private String username;
  private String displayName;
  private String phoneNumber;
  private Address address;
  private String avatarPath;
  private boolean enabled;
}

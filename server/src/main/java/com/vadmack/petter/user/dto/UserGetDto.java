package com.vadmack.petter.user.dto;

import com.vadmack.petter.ad.dto.AdGetDto;
import com.vadmack.petter.user.Address;
import lombok.Data;

import java.util.Set;

@Data
public class UserGetDto {
  private String id;
  private String email;
  private String username;
  private String displayName;
  private String phoneNumber;
  private Address address;
  private String imageId;
  private Set<AdGetDto> ads;
  private Set<AdGetDto> favoriteAds;
}

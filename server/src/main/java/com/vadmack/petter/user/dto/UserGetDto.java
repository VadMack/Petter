package com.vadmack.petter.user.dto;

import com.vadmack.petter.ad.Ad;
import com.vadmack.petter.ad.dto.AdGetDto;
import com.vadmack.petter.user.Address;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserGetDto {
  private String id;
  private String email;
  private String username;
  private String displayName;
  private String phoneNumber;
  private Address address;
  private Set<String> imageIds = new HashSet<>();
  private Set<AdGetDto> ads;
  private Set<AdGetDto> favoriteAds;
}

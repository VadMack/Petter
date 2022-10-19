package com.vadmack.petter.ad.dto;

import com.vadmack.petter.ad.AdState;
import com.vadmack.petter.ad.Gender;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class AdGetListDto {
  private String id;
  private String ownerId;
  private String name;
  private String price;
  private String breed;
  private Gender gender;
  private LocalDate birthDate;
  private boolean hasAchievements;
  private Set<String> imagePaths;
  private AdState state;
}


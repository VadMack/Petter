package com.vadmack.petter.ad.dto;

import com.vadmack.petter.ad.AdState;
import com.vadmack.petter.ad.Gender;
import com.vadmack.petter.ad.Species;
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
  private Species species;
  private Integer price;
  private String breed;
  private Gender gender;
  private LocalDate birthDate;
  private boolean hasAchievements;
  private Set<String> imagePaths;
  private AdState state;
}


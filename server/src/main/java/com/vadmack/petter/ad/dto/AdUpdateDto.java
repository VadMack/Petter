package com.vadmack.petter.ad.dto;

import com.vadmack.petter.ad.AdState;
import com.vadmack.petter.ad.CompetitionStatus;
import com.vadmack.petter.ad.Gender;
import com.vadmack.petter.ad.Species;
import com.vadmack.petter.app.model.ModelUpdateDto;
import com.vadmack.petter.app.model.Address;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@Data
public class AdUpdateDto implements ModelUpdateDto {
  private String name;
  private Integer price;
  private Species species;
  private String breed;
  private Gender gender;
  private LocalDate birthDate;
  private Short height;
  private Short weight;
  private Map<String, CompetitionStatus> achievements;
  private Set<String> vaccinations;
  private String description;
  private AdState state;
  private Address address;
}

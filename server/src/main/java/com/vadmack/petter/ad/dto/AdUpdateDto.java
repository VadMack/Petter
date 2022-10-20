package com.vadmack.petter.ad.dto;

import com.vadmack.petter.ad.Gender;
import com.vadmack.petter.ad.Species;
import com.vadmack.petter.app.model.ModelUpdateDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@Data
public class AdUpdateDto implements ModelUpdateDto {
  private String name;
  private String price;
  private Species species;
  private String breed;
  private Gender gender;
  private LocalDate birthDate;
  private Short height;
  private Short weight;
  private Map<String, String> achievements;
  private Set<String> vaccinations;
  private String description;
}

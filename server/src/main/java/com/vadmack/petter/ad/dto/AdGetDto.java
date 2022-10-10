package com.vadmack.petter.ad.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.vadmack.petter.ad.AdState;
import com.vadmack.petter.ad.Gender;
import com.vadmack.petter.ad.Species;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class AdGetDto {
  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId id;
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
  @JsonSerialize(using = ToStringSerializer.class)
  private Set<ObjectId> imageIds = new HashSet<>();
  private AdState state;
}

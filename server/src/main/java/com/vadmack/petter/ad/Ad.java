package com.vadmack.petter.ad;

import com.vadmack.petter.app.model.MongoModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Document("ads")
public class Ad extends MongoModel {
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
  private Set<String> imagePaths = new HashSet<>();
  private AdState state;
  private String ownerId;

}

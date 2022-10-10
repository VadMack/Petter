package com.vadmack.petter.ad;

import com.vadmack.petter.file.FileMetadata;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@Document("ads")
public class Ad {
  @MongoId
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
  @DBRef
  private Set<FileMetadata> images = new HashSet<>();
  private AdState state;

}

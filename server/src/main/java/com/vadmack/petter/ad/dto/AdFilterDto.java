package com.vadmack.petter.ad.dto;

import com.vadmack.petter.ad.AdState;
import com.vadmack.petter.ad.Gender;
import com.vadmack.petter.ad.Species;
import com.vadmack.petter.app.model.ModelFilter;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class AdFilterDto implements ModelFilter {
  private String ownerId;
  private AdState state;
  private Species species;
  private String breed;
  private Gender gender;

  private Integer minPrice;
  private Integer maxPrice;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate maxCreationDate;

  private String notOwnerId;
}

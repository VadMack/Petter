package com.vadmack.petter.ad.dto;

import com.vadmack.petter.ad.AdState;
import com.vadmack.petter.ad.Gender;
import com.vadmack.petter.ad.Species;
import com.vadmack.petter.app.model.ModelFilter;
import lombok.Data;

@Data
public class AdFilterDto implements ModelFilter {
  private String ownerId;
  private AdState state;
  private Species species;
  private String breed;
  private Gender gender;

  private Integer minPrice;
  private Integer maxPrice;

  private String notOwnerId;
}

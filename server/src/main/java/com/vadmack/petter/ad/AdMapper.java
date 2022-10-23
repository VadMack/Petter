package com.vadmack.petter.ad;

import com.vadmack.petter.ad.dto.AdGetListDto;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@UtilityClass
public class AdMapper {

  public static List<AdGetListDto> entityListToDto(Collection<Ad> entities) {
    List<AdGetListDto> result = new ArrayList<>();
    entities.forEach(ad -> {
      AdGetListDto dto = AdGetListDto.builder()
              .id(ad.getId())
              .ownerId(ad.getOwnerId())
              .name(ad.getName())
              .price(ad.getPrice())
              .species(ad.getSpecies())
              .breed(ad.getBreed())
              .gender(ad.getGender())
              .birthDate(ad.getBirthDate())
              .hasAchievements(!ad.getAchievements().isEmpty())
              .imagePaths(ad.getImagePaths())
              .state(ad.getState()).build();
      result.add(dto);
    });
    return result;
  }
}

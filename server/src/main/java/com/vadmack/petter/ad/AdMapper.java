package com.vadmack.petter.ad;

import com.vadmack.petter.ad.dto.AdGetListDto;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@UtilityClass
public class AdMapper {

  public static @NotNull List<AdGetListDto> entityListToDto(@NotNull Collection<Ad> entities,
                                                            @NotNull Set<String> favoriteAdIds) {
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
              .state(ad.getState())
              .liked(favoriteAdIds.contains(ad.getId()))
              .build();
      result.add(dto);
    });
    return result;
  }
}

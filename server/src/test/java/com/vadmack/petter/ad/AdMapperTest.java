package com.vadmack.petter.ad;

import com.vadmack.petter.ad.dto.AdGetListDto;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AdMapperTest {

  @Test
  void entityListToDtoList() {
    Ad ad1 = new Ad();
    ad1.setName("Banana");
    Ad ad2 = new Ad();
    ad2.setName("Svetlana");
    List<Ad> ads = List.of(ad1, ad2);

    AdGetListDto dto1 = AdGetListDto.builder().name("Banana").imagePaths(new HashSet<>()).build();
    AdGetListDto dto2 = AdGetListDto.builder().name("Svetlana").imagePaths(new HashSet<>()).build();
    List<AdGetListDto> expected = List.of(dto1, dto2);

    var result = AdMapper.entityListToDto(ads, new HashSet<>());
    assertAll(() -> {
      assertNotNull(result);
      assertEquals(expected, result);
    });
  }

  @Test
  void entityListToDtoSet() {
    Ad ad1 = new Ad();
    ad1.setName("Banana");
    Ad ad2 = new Ad();
    ad2.setName("Svetlana");
    Set<Ad> ads = Set.of(ad1, ad2);

    AdGetListDto dto1 = AdGetListDto.builder().name("Banana").imagePaths(new HashSet<>()).build();
    AdGetListDto dto2 = AdGetListDto.builder().name("Svetlana").imagePaths(new HashSet<>()).build();
    Set<AdGetListDto> expected = Set.of(dto1, dto2);

    var result = AdMapper.entityListToDto(ads, new HashSet<>());
    assertAll(() -> {
      assertNotNull(result);
      assertEquals(expected, new HashSet<>(result));
    });
  }

  @Test
  void entityListToDtoWithFavourites() {
    Ad ad1 = new Ad();
    ad1.setId("63665ea8dbf8ff24213da087");
    ad1.setName("Banana");
    Ad ad2 = new Ad();
    ad2.setId("63665ea8dbf8ff24213da088");
    ad2.setName("Svetlana");
    Set<Ad> ads = Set.of(ad1, ad2);

    AdGetListDto dto1 = AdGetListDto.builder()
            .id("63665ea8dbf8ff24213da087").name("Banana").imagePaths(new HashSet<>()).liked(true)
            .build();
    AdGetListDto dto2 = AdGetListDto.builder()
            .id("63665ea8dbf8ff24213da088").name("Svetlana").imagePaths(new HashSet<>()).liked(false)
            .build();
    Set<AdGetListDto> expected = Set.of(dto1, dto2);

    var result = AdMapper.entityListToDto(ads, Set.of("63665ea8dbf8ff24213da087"));
    assertAll(() -> {
      assertNotNull(result);
      assertEquals(expected, new HashSet<>(result));
    });
  }
}
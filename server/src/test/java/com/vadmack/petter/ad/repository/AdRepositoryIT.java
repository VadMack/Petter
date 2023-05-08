package com.vadmack.petter.ad.repository;

import com.vadmack.petter.ad.Ad;
import com.vadmack.petter.ad.Gender;
import com.vadmack.petter.ad.Species;
import com.vadmack.petter.ad.dto.AdFilterDto;
import com.vadmack.petter.ad.dto.AdUpdateDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataMongoTest
class AdRepositoryIT {

  @Autowired
  private AdRepository adRepository;

  private List<Ad> ads;


  private static final String CLEAN_ME_TAG = "cleanMe";
  private List<Ad> variableAds = new ArrayList<>();

  @BeforeAll
  void setup() {
    Ad ad1 = new Ad();
    ad1.setSpecies(Species.CAT);
    ad1.setGender(Gender.MALE);
    ad1.setPrice(5000);
    ad1.setOwnerId("63665ea8dbf8ff24213da087");

    Ad ad2 = new Ad();
    ad2.setSpecies(Species.DOG);
    ad2.setGender(Gender.MALE);
    ad2.setPrice(10000);
    ad2.setOwnerId("63665ea8dbf8ff24213da087");

    Ad ad3 = new Ad();
    ad3.setSpecies(Species.CAT);
    ad3.setGender(Gender.FEMALE);
    ad3.setPrice(15000);
    ad3.setOwnerId("63665ea8dbf8ff24213da087");

    Ad ad4 = new Ad();
    ad4.setSpecies(Species.DOG);
    ad4.setGender(Gender.FEMALE);
    ad4.setPrice(20000);
    ad4.setOwnerId("63665ea8dbf8ff24213da099");

    ads = adRepository.saveAll(List.of(ad1, ad2, ad3, ad4));
  }

  @Test
  void findByProperties1() {
    AdFilterDto filter = new AdFilterDto();
    filter.setGender(Gender.MALE);

    var result = adRepository.findByProperties(filter, PageRequest.of(0, 4));

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(2, result.size());
      assertIterableEquals(List.of(ads.get(0), ads.get(1)), result);
    });
  }

  @Test
  void findByProperties2() {
    AdFilterDto filter = new AdFilterDto();
    filter.setGender(Gender.MALE);
    filter.setSpecies(Species.DOG);

    var result = adRepository.findByProperties(filter, PageRequest.of(0, 4));

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(1, result.size());
      assertIterableEquals(List.of(ads.get(1)), result);
    });
  }

  @Test
  void findByProperties3() {
    AdFilterDto filter = new AdFilterDto();
    filter.setGender(Gender.MALE);

    var result = adRepository.findByProperties(filter, PageRequest.of(1, 1));

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(1, result.size());
      assertIterableEquals(List.of(ads.get(1)), result);
    });
  }

  @Test
  void findByProperties4() {
    AdFilterDto filter = new AdFilterDto();

    var result = adRepository.findByProperties(filter, PageRequest.of(0, 4));

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(4, result.size());
      assertIterableEquals(ads, result);
    });
  }

  @Test
  void findByProperties5() {
    AdFilterDto filter = new AdFilterDto();
    filter.setMaxPrice(6000);

    var result = adRepository.findByProperties(filter, PageRequest.of(0, 4));

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(1, result.size());
      assertIterableEquals(List.of(ads.get(0)), result);
    });
  }

  @Test
  void findByProperties7() {
    AdFilterDto filter = new AdFilterDto();
    filter.setMaxPrice(5000);

    var result = adRepository.findByProperties(filter, PageRequest.of(0, 4));

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(1, result.size());
      assertIterableEquals(List.of(ads.get(0)), result);
    });
  }

  @Test
  void findByProperties8() {
    AdFilterDto filter = new AdFilterDto();
    filter.setMinPrice(6000);
    filter.setMaxPrice(16000);

    var result = adRepository.findByProperties(filter, PageRequest.of(0, 4));

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(2, result.size());
      assertIterableEquals(List.of(ads.get(1), ads.get(2)), result);
    });
  }

  @Test
  void findByProperties9() {
    AdFilterDto filter = new AdFilterDto();
    filter.setNotOwnerId("63665ea8dbf8ff24213da099");

    var result = adRepository.findByProperties(filter, PageRequest.of(0, 4));

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(3, result.size());
      assertIterableEquals(List.of(ads.get(0), ads.get(1), ads.get(2)), result);
    });
  }

  @Test
  void findByProperties10() {
    AdFilterDto filter = new AdFilterDto();
    filter.setSpecies(Species.CAT);
    filter.setMinPrice(14000);
    filter.setNotOwnerId("63665ea8dbf8ff24213da099");

    var result = adRepository.findByProperties(filter, PageRequest.of(0, 4));

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(1, result.size());
      assertIterableEquals(List.of(ads.get(2)), result);
    });
  }

  @Test
  @Tag(CLEAN_ME_TAG)
  void updateById() {
    Ad ad = new Ad();
    ad.setName("Ne Bober");
    ad = adRepository.save(ad);
    variableAds.add(ad);
    String expectedName = "Bober";

    AdUpdateDto dto = new AdUpdateDto();
    dto.setName(expectedName);

    adRepository.updateById(dto, ad.getId());
    var result = adRepository.findById(ad.getId());

    assertAll(() -> {
      assertThat(result).isNotEmpty();
      assertEquals(expectedName, result.get().getName());
    });
  }

  @AfterEach
  void tearDown(TestInfo info) {
    if (!info.getTags().contains(CLEAN_ME_TAG)) {
      return;
    }

    adRepository.deleteAll(variableAds);
    variableAds.clear();
  }

  @AfterAll
  void cleanup() {
    adRepository.deleteAll(ads);
  }

}

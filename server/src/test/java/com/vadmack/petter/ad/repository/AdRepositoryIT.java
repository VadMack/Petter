package com.vadmack.petter.ad.repository;

import com.vadmack.petter.ad.Ad;
import com.vadmack.petter.ad.Gender;
import com.vadmack.petter.ad.Species;
import com.vadmack.petter.ad.dto.AdFilterDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataMongoTest
class AdRepositoryIT {

  @Autowired
  AdRepository adRepository;

  private List<Ad> ads;

  @BeforeAll
  private void setup() {
    Ad ad1 = new Ad();
    ad1.setSpecies(Species.CAT);
    ad1.setGender(Gender.MALE);

    Ad ad2 = new Ad();
    ad2.setSpecies(Species.DOG);
    ad2.setGender(Gender.MALE);

    Ad ad3 = new Ad();
    ad3.setSpecies(Species.CAT);
    ad3.setGender(Gender.FEMALE);

    Ad ad4 = new Ad();
    ad4.setSpecies(Species.DOG);
    ad4.setGender(Gender.FEMALE);

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

  @AfterAll
  void cleanup() {
    adRepository.deleteAll(ads);
  }

}

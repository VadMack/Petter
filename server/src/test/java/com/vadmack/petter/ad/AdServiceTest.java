package com.vadmack.petter.ad;

import com.vadmack.petter.ad.dto.AdCreateDdo;
import com.vadmack.petter.ad.dto.AdGetDto;
import com.vadmack.petter.ad.repository.AdRepository;
import com.vadmack.petter.app.exception.NotFoundException;
import com.vadmack.petter.user.User;
import com.vadmack.petter.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AdServiceTest {

  @InjectMocks
  private AdService adService;

  @Spy
  private ModelMapper modelMapper;

  @Mock
  private AdRepository adRepository;

  @Mock
  private UserService userService;

  @Test
  void create() {
    AdCreateDdo dto = new AdCreateDdo();
    dto.setName("Bobik");
    dto.setGender(Gender.MALE);
    dto.setBirthDate(LocalDate.now());

    User user = new User();
    user.setId("63665ea8dbf8ff24213da089");

    Ad ad = new Ad();
    ad.setName("Bobik");
    ad.setGender(Gender.MALE);
    ad.setBirthDate(LocalDate.now());
    ad.setId("63665ea8dbf8ff24213da087");
    ad.setOwnerId(user.getId());
    ad.setState(AdState.OPEN);
    ad.setCreationDate(LocalDate.now());


    when(adRepository.save(any(Ad.class))).thenReturn(ad);
    doNothing().when(userService).addAd(ad.getId(), user.getId());

    AdGetDto expected = new AdGetDto();
    expected.setName("Bobik");
    expected.setGender(Gender.MALE);
    expected.setBirthDate(LocalDate.now());
    expected.setId("63665ea8dbf8ff24213da087");
    expected.setOwnerId(user.getId());
    expected.setState(AdState.OPEN);
    expected.setCreationDate(LocalDate.now());
    expected.setAchievements(new HashMap<>());
    expected.setVaccinations(new HashSet<>());

    var result = adService.create(dto, user);
    assertAll(() -> {
      assertNotNull(result);
      assertEquals(expected, result);
    });
  }

  @Test
  void getById() {
    String id = "63665ea8dbf8ff24213da087";
    String name = "Tiger";

    Ad ad = new Ad();
    ad.setName(name);
    when(adRepository.findById(id)).thenReturn(Optional.of(ad));
    var result = adService.getById(id);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(name, result.getName());
    });
  }

  @Test
  void getByIdNotFound() {
    String id = "63665ea8dbf8ff24213da087";

    when(adRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> adService.getById(id));
  }
}
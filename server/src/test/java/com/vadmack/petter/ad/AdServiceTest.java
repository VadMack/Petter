package com.vadmack.petter.ad;

import com.vadmack.petter.ad.repository.AdRepository;
import com.vadmack.petter.app.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AdServiceTest {

  @InjectMocks
  private AdService adService;

  @Mock
  private AdRepository adRepository;

  @Test
  void getById() {
    String id = "63665ea8dbf8ff24213da087";
    String name= "Tiger";

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
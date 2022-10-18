package com.vadmack.petter.ad.repository;

import com.vadmack.petter.ad.Ad;
import com.vadmack.petter.ad.AdState;
import com.vadmack.petter.ad.Gender;
import com.vadmack.petter.ad.Species;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface AdRepositoryCustom {
  void addImage(String imagePath, String adId);

  List<Ad> findByProperties(String ownerId,
                            AdState state,
                            Species species,
                            String breed,
                            Gender gender,
                            Pageable page);
}

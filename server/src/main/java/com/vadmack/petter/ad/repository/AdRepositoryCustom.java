package com.vadmack.petter.ad.repository;

import com.vadmack.petter.ad.Ad;
import com.vadmack.petter.ad.dto.AdFilterDto;
import com.vadmack.petter.ad.dto.AdUpdateDto;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface AdRepositoryCustom {
  void addImage(String imagePath, String adId);

  List<Ad> findByProperties(AdFilterDto adFilterDto,
                            Pageable page);

  void updateById(AdUpdateDto dto, String id);
}

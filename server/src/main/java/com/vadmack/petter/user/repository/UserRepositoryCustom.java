package com.vadmack.petter.user.repository;

import com.vadmack.petter.ad.Ad;
import com.vadmack.petter.user.dto.UserUpdateDto;


public interface UserRepositoryCustom {
  void addAd(Ad ad, String userId);

  void addFavouriteAd(Ad ad, String userId);

  void updateById(UserUpdateDto updates, String userId);
}

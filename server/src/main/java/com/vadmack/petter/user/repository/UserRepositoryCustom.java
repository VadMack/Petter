package com.vadmack.petter.user.repository;

import com.vadmack.petter.ad.Ad;


public interface UserRepositoryCustom {
  void addAd(Ad ad, String userId);

  void addFavouriteAd(Ad ad, String userId);
}

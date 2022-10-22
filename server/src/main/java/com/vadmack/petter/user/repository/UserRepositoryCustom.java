package com.vadmack.petter.user.repository;

import com.vadmack.petter.user.dto.UserUpdateDto;


public interface UserRepositoryCustom {
  void addAdId(String adId, String userId);

  void addFavouriteAdId(String adId, String userId);

  void updateById(UserUpdateDto updates, String userId);
}

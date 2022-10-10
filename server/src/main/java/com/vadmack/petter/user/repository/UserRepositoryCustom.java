package com.vadmack.petter.user.repository;

import com.vadmack.petter.ad.Ad;
import com.vadmack.petter.file.FileMetadata;


public interface UserRepositoryCustom {
  void addImage(FileMetadata fileMetadata, String userId);
  void addAd(Ad ad, String userId);
  void addFavouriteAd(Ad ad, String userId);
}

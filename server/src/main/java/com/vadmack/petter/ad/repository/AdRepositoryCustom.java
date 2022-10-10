package com.vadmack.petter.ad.repository;

import com.vadmack.petter.file.FileMetadata;

public interface AdRepositoryCustom {
  void addImage(FileMetadata fileMetadata, String adId);
}

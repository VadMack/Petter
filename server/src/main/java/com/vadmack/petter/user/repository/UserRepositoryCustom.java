package com.vadmack.petter.user.repository;

import com.vadmack.petter.file.FileMetadata;

public interface UserRepositoryCustom {
  void addImage(FileMetadata fileMetadata, String userId);
}

package com.vadmack.petter.file;

import com.vadmack.petter.app.utils.AppUtils;
import com.vadmack.petter.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@RequiredArgsConstructor
@Service
public class FileMetadataService {

  private final FileMetadataRepository fileMetadataRepository;

  public void save(FileMetadata metadata) {
    fileMetadataRepository.save(metadata);
  }

  public void validatePath(Path path) {
    validatePath(path.toString());
  }

  public void validatePath(String path) {
    if (path.contains("..")) {
      throw new ValidationException("Path cannot contain '..'");
    }

    if (existsByRelativePath(path)) {
      throw new ValidationException("File by this path already exists");
    }
  }

  public @NotNull FileMetadata getById(String id) {
    return AppUtils.checkFound(fileMetadataRepository.findById(new ObjectId(id)),
            String.format("File metadata with id=%s not found", id));
  }

  private boolean existsByRelativePath(String relativePath) {
    return fileMetadataRepository.existsByRelativePath(relativePath);
  }
}

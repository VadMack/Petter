package com.vadmack.petter.file;

import com.vadmack.petter.app.exception.NotFoundException;
import com.vadmack.petter.app.exception.ValidationException;
import com.vadmack.petter.app.utils.AppUtils;
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

  public @NotNull FileMetadata getById(String id) {
    return AppUtils.checkFound(fileMetadataRepository.findById(new ObjectId(id)),
            String.format("File metadata with id=%s not found", id));
  }

  public void validatePathForSave(Path path) {
    validatePathForSave(path.toString());
  }

  public void validatePathForSave(String path) {
    if (outsideDirectory(path)) {
      throw new ValidationException("Path cannot contain '..'");
    }

    if (existsByRelativePath(path)) {
      throw new ValidationException("File by this path already exists");
    }
  }

  public void validatePathForGet(Path path) {
    validatePathForGet(path.toString());
  }

  public void validatePathForGet(String path) {
    if (outsideDirectory(path)) {
      throw new ValidationException("Path cannot contain '..'");
    }

    if (!existsByRelativePath(path)) {
      throw new NotFoundException(String.format("File by path=%s does not exist", path));
    }
  }

  public boolean outsideDirectory(String path) {
    return path.contains("..");
  }

  private boolean existsByRelativePath(String relativePath) {
    return fileMetadataRepository.existsByRelativePath(relativePath);
  }
}

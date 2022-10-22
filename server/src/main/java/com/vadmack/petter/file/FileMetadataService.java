package com.vadmack.petter.file;

import com.vadmack.petter.ad.Ad;
import com.vadmack.petter.ad.AdService;
import com.vadmack.petter.app.exception.NotFoundException;
import com.vadmack.petter.app.exception.ValidationException;
import com.vadmack.petter.app.utils.AppUtils;
import com.vadmack.petter.user.User;
import com.vadmack.petter.user.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Lazy})
@Service
public class  FileMetadataService {

  private final FileMetadataRepository fileMetadataRepository;
  private final UserService userService;
  private final AdService adService;

  public void save(FileMetadata metadata) {
    fileMetadataRepository.save(metadata);
  }

  public @NotNull FileMetadata getById(String id) {
    return AppUtils.checkFound(fileMetadataRepository.findById(new ObjectId(id)),
            String.format("File metadata with id=%s not found", id));
  }

  public Optional<FileMetadata> findById(String id) {
    return fileMetadataRepository.findById(new ObjectId(id));
  }

  public @NotNull FileMetadata getByRelativePath(String relativePath) {
    return AppUtils.checkFound(findByRelativePath(relativePath),
            String.format("File metadata with relativePath=%s not found", relativePath));
  }

  public Optional<FileMetadata> findByRelativePath(String relativePath) {
    return fileMetadataRepository.findByRelativePath(relativePath);
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

  public boolean existsByRelativePath(String relativePath) {
    return fileMetadataRepository.existsByRelativePath(relativePath);
  }

  @Transactional
  public void deleteByRelativePath(String relativePath) {
    FileMetadata metadata = getByRelativePath(relativePath);
    Attachment attachment = metadata.getAttachment();
    unlinkFromAttachment(attachment, relativePath);
    fileMetadataRepository.deleteByRelativePath(relativePath);
  }

  private void unlinkFromAttachment(Attachment attachment, String relativePath) {
    String attachmentId = attachment.getId();
    switch (attachment.getType()) {
      case USER -> {
        User user = userService.getById(attachmentId);
        user.setAvatarPath(null);
        userService.save(user);
      }
      case AD -> {
        Ad ad = adService.getById(attachmentId);
        ad.getImagePaths().remove(relativePath);
        adService.save(ad);
      }
    }
  }
}

package com.vadmack.petter.file;

import com.vadmack.petter.app.exception.ServerSideException;
import com.vadmack.petter.app.exception.ValidationException;
import com.vadmack.petter.file.metadata.Attachment;
import com.vadmack.petter.file.metadata.AttachmentType;
import com.vadmack.petter.file.metadata.FileMetadata;
import com.vadmack.petter.file.metadata.FileMetadataService;
import com.vadmack.petter.user.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

  public static final String USERS_PHOTO_STORAGE_FOLDER_NAME = "users";

  private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of("image/png", "image/jpeg", "image/jpg");

  @Value("${photo-storage.path}")
  private String photoStorage;

  private final FileMetadataService fileMetadataService;

  @PostConstruct
  public void init() {
    try {
      Files.createDirectories(Paths.get(photoStorage, USERS_PHOTO_STORAGE_FOLDER_NAME));
    } catch (IOException ex) {
      throw new ServerSideException("Could not create upload folder. " + ex.getMessage());
    }
  }

  /**
   * For messages attachment will be set later during message sending
   */
  @Transactional
  public FileMetadata save(@NotNull MultipartFile image,
                           @NotNull String userId,
                           @NotNull AttachmentType attachmentType,
                           String attachmentId) {
    validateContentType(image);
    String generatedFilename = generateFilename(FilenameUtils.getExtension(image.getOriginalFilename()));
    Path relativePath = Paths.get(photoStorage, USERS_PHOTO_STORAGE_FOLDER_NAME, userId, generatedFilename);
    fileMetadataService.validatePathForSave(relativePath);
    try {
      Files.createDirectories(Paths.get(photoStorage, USERS_PHOTO_STORAGE_FOLDER_NAME, userId));
      image.transferTo(relativePath);
    } catch (IOException ex) {
      throw new ServerSideException("Could not save file. " + ex.getMessage());
    }

    FileMetadata fileMetadata = FileMetadata.builder()
            .relativePath(Paths.get(photoStorage).relativize(relativePath).toString())
            .originalFilename(generatedFilename)
            .contentType(image.getContentType())
            .size(image.getSize())
            .attachment(new Attachment(attachmentType, attachmentId)).build();

    fileMetadataService.save(fileMetadata);
    return fileMetadata;
  }

  public Resource getByRelativePath(@NotNull Path relativePath) {
    fileMetadataService.validatePathForGet(relativePath);
    return new FileSystemResource(Paths.get(photoStorage, relativePath.toString()));
  }

  private void validateContentType(@NotNull MultipartFile file) {
    if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
      throw new ValidationException("Allowed content types: " + ALLOWED_CONTENT_TYPES);
    }
  }

  private String generateFilename(@NotNull String extension) {
    return UUID.randomUUID() + "." + extension;
  }

  @Transactional
  public void unlinkAndDeleteByRelativePath(@NotNull Path relativePath) {
    unlinkAndDeleteByRelativePath(relativePath.toString());
  }

  @Transactional
  public void unlinkAndDeleteByRelativePath(@NotNull String relativePath) {
    fileMetadataService.unlinkAndDeleteByRelativePath(relativePath);
    deleteFile(relativePath);
  }

  @Transactional
  public void deleteByRelativePath(@NotNull String relativePath) {
    fileMetadataService.deleteByRelativePath(relativePath);
    deleteFile(relativePath);
  }

  @Transactional
  public void deleteByRelativePaths(@NotNull Collection<String> relativePaths) {
    relativePaths.forEach(this::deleteByRelativePath);
  }

  private void deleteFile(@NotNull String relativePath) {
    try {
      Files.delete(Paths.get(photoStorage, relativePath));
    } catch (IOException ex) {
      throw new ServerSideException("Could not delete file. " + ex.getMessage());
    }
  }

}

package com.vadmack.petter.file;

import com.vadmack.petter.app.exception.ServerSideException;
import com.vadmack.petter.app.exception.ValidationException;
import com.vadmack.petter.user.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
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
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

  static final String USERS_PHOTO_STORAGE_FOLDER_NAME = "users";

  private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of("image/png", "image/jpeg", "image/jpg");

  @Value("${photo-storage.path}")
  private String photoStorage;

  private final FileMetadataService fileMetadataService;

  @PostConstruct
  public void init() {
    try {
      Files.createDirectories(Paths.get(photoStorage, USERS_PHOTO_STORAGE_FOLDER_NAME));
    } catch (IOException ex) {
      throw new ServerSideException("Could not create upload folder: " + ex.getMessage());
    }
  }

  @Transactional
  public FileMetadata save(MultipartFile image, String userId) {
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
            .size(image.getSize()).build();

    fileMetadataService.save(fileMetadata);
    return fileMetadata;
  }

  public Resource getByRelativePath(Path relativePath) {
    fileMetadataService.validatePathForGet(relativePath);
    return new FileSystemResource(Paths.get(photoStorage, relativePath.toString()));
  }

  private void validateContentType(MultipartFile file) {
    if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
      throw new ValidationException("Allowed content types: " + ALLOWED_CONTENT_TYPES);
    }
  }

  private String generateFilename(String extension) {
    return UUID.randomUUID() + "." + extension;
  }

  public boolean isOwner(User user, String folderName) {
    return user.getId().equals(folderName);
  }
}

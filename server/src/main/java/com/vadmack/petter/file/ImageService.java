package com.vadmack.petter.file;

import com.vadmack.petter.exception.ServerSideException;
import com.vadmack.petter.exception.ValidationException;
import com.vadmack.petter.user.User;
import com.vadmack.petter.user.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
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
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ImageService {

  private static final String USERS_PHOTO_STORAGE_FOLDER_NAME = "users";

  private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of("image/png", "image/jpeg", "image/jpg");

  @Value("${photo-storage.path}")
  private String photoStorage;

  private final FileMetadataService fileMetadataService;
  private final UserService userService;

  @PostConstruct
  public void init() {
    try {
      Files.createDirectories(Paths.get(photoStorage, USERS_PHOTO_STORAGE_FOLDER_NAME));
    } catch (IOException ex) {
      throw new ServerSideException("Could not create upload folder: " + ex.getMessage());
    }
  }

  @Transactional
  public void save(MultipartFile image, String userId) {
    validateContentType(image);
    Path relativePath = Paths.get(photoStorage, USERS_PHOTO_STORAGE_FOLDER_NAME, userId, image.getOriginalFilename());
    fileMetadataService.validatePath(relativePath);
    try {
      Files.createDirectories(Paths.get(photoStorage, USERS_PHOTO_STORAGE_FOLDER_NAME, userId));
      image.transferTo(relativePath);
    } catch (IOException ex) {
      throw new ServerSideException("Could not save file. " + ex.getMessage());
    }

    FileMetadata fileMetadata = FileMetadata.builder()
            .relativePath(relativePath.toString())
            .originalFilename(image.getOriginalFilename())
            .contentType(image.getContentType())
            .size(image.getSize()).build();

    fileMetadataService.save(fileMetadata);
    userService.addImage(fileMetadata, userId);

  }

  public Resource getById(String id) {
    FileMetadata metadata = fileMetadataService.getById(id);
    return new FileSystemResource(metadata.getRelativePath());
  }

  private void validateContentType(MultipartFile file) {
    if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
      throw new ValidationException("Allowed content types: " + ALLOWED_CONTENT_TYPES);
    }
  }

  public boolean isOwner(User user, String imageId) {
    return user.getImages().stream().map(FileMetadata::getId).toList().contains(new ObjectId(imageId));
  }
}

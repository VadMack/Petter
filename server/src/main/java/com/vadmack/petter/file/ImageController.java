package com.vadmack.petter.file;

import com.vadmack.petter.app.controller.SecuredRestController;
import com.vadmack.petter.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Paths;

import static com.vadmack.petter.file.ImageService.USERS_PHOTO_STORAGE_FOLDER_NAME;

@RequiredArgsConstructor
@RequestMapping("api/files")
@RestController
public class ImageController implements SecuredRestController {

  private final ImageService imageService;

  @PreAuthorize("@imageService.isOwner(#user, #folderName)")
  @GetMapping(value ="/" +  USERS_PHOTO_STORAGE_FOLDER_NAME + "/{folderName}/{fileName:.+}",
          produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
  public ResponseEntity<Resource> downloadImage(@AuthenticationPrincipal User user,
                                                @PathVariable String folderName,
                                                @PathVariable String fileName) {
    Resource image = imageService.getByRelativePath(Paths.get(USERS_PHOTO_STORAGE_FOLDER_NAME,
            folderName, fileName));
    return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + image.getFilename() + "\"")
            .body(image);
  }

  @PreAuthorize("@imageService.isOwner(#user, #folderName)")
  @DeleteMapping(value ="/" +  USERS_PHOTO_STORAGE_FOLDER_NAME + "/{folderName}/{fileName:.+}",
          produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
  public ResponseEntity<Resource> deleteImage(@AuthenticationPrincipal User user,
                                                @PathVariable String folderName,
                                                @PathVariable String fileName) {
    imageService.deleteByRelativePath(Paths.get(USERS_PHOTO_STORAGE_FOLDER_NAME,
            folderName, fileName));
    return new ResponseEntity<>(HttpStatus.OK);
  }
}

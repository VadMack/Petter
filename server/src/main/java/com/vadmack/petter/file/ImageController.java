package com.vadmack.petter.file;

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
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("api/files")
@RestController
public class ImageController {

  private final ImageService imageService;

  @PostMapping
  public ResponseEntity<?> uploadImage(@AuthenticationPrincipal User user,
                                       @RequestParam MultipartFile image) {
    imageService.save(image, user.getId().toString());
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PreAuthorize("@imageService.isOwner(#user, #id)")
  @GetMapping(produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
  public ResponseEntity<Resource> uploadImage(@AuthenticationPrincipal User user,
                                              @RequestParam String id) {
    Resource image = imageService.getById(id);
    return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + image.getFilename() + "\"")
            .body(image);
  }
}

package com.vadmack.petter.file;

import com.vadmack.petter.app.controller.SecuredRestController;
import com.vadmack.petter.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("api/files")
@RestController
public class ImageController implements SecuredRestController {

  private final ImageService imageService;

  @PreAuthorize("@imageService.isOwner(#user, #id)")
  @GetMapping(value = "/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
  public ResponseEntity<Resource> downloadImage(@AuthenticationPrincipal User user,
                                                @PathVariable String id) {
    Resource image = imageService.getById(id);
    return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + image.getFilename() + "\"")
            .body(image);
  }
}

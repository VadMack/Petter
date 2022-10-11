package com.vadmack.petter.ad;

import com.vadmack.petter.ad.dto.AdCreateDdo;
import com.vadmack.petter.ad.dto.AdGetDto;
import com.vadmack.petter.app.annotation.SecuredRestController;
import com.vadmack.petter.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/ads")
@RestController
public class AdController implements SecuredRestController {

  private final AdService adService;

  @PostMapping
  public ResponseEntity<?> create(@AuthenticationPrincipal User user,
                                  @RequestBody AdCreateDdo dto) {
    adService.create(dto, user.getId().toString());
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<AdGetDto>> findAll() {
    return ResponseEntity.ok(adService.findAll());
  }

  @PutMapping("/{id}/like")
  public ResponseEntity<?> like(@AuthenticationPrincipal User user,
                                @PathVariable String id) {
    adService.like(id, user.getId().toString());
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PreAuthorize("@adService.isOwner(#user, #id)")
  @PostMapping(value = "{id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> uploadImage(@AuthenticationPrincipal User user,
                                       @RequestParam MultipartFile image,
                                       @PathVariable String id) {
    adService.addImage(image, id, user.getId().toString());
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

}

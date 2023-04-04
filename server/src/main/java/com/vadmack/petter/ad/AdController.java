package com.vadmack.petter.ad;

import com.vadmack.petter.ad.dto.*;
import com.vadmack.petter.app.controller.SecuredRestController;
import com.vadmack.petter.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
  public ResponseEntity<AdGetDto> create(@AuthenticationPrincipal User user,
                                  @RequestBody AdCreateDdo dto) {
    return ResponseEntity.ok(adService.create(dto, user));
  }

  @GetMapping
  public ResponseEntity<List<AdGetListDto>> getByProperties(@AuthenticationPrincipal User user,
                                                            AdFilterDto filter,
                                                            Pageable pageable) {
    return ResponseEntity.ok(adService.getDtoByProperties(filter, pageable, user.getFavoriteAdIds()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<AdGetDto> getById(@AuthenticationPrincipal User user,
                                          @PathVariable String id) {
    return ResponseEntity.ok(adService.getDtoById(id, user.getFavoriteAdIds()));
  }

  @PreAuthorize("@adService.isOwner(#user, #id)")
  @PutMapping("/{id}")
  public ResponseEntity<?> updateById(@AuthenticationPrincipal User user,
                                      @PathVariable String id,
                                      @RequestBody AdUpdateDto dto) {
    adService.updateById(dto, id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PreAuthorize("@adService.isOwner(#user, #id)")
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteById(@AuthenticationPrincipal User user,
                                      @PathVariable String id) {
    adService.deleteWithDependenciesById(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/{id}/like")
  public ResponseEntity<?> changeLikeStatus(@AuthenticationPrincipal User user,
                                            @PathVariable String id,
                                            @RequestParam boolean enable) {
    adService.changeLikeStatus(id, enable, user.getId());
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PreAuthorize("@adService.isOwner(#user, #id)")
  @PostMapping(value = "{id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> uploadImage(@AuthenticationPrincipal User user,
                                       @RequestParam MultipartFile image,
                                       @PathVariable String id) {
    adService.addImage(image, id, user.getId());
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

}

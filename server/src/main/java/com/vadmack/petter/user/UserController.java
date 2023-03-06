package com.vadmack.petter.user;

import com.vadmack.petter.ad.dto.AdGetListDto;
import com.vadmack.petter.app.annotation.Secured;
import com.vadmack.petter.user.dto.UserGetDto;
import com.vadmack.petter.user.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/users")
@RestController
public class UserController {

  private final UserService userService;

  @Secured
  @GetMapping
  public ResponseEntity<List<UserGetDto>> getAll() {
    return ResponseEntity.ok(userService.findAllDto());
  }

  @Secured
  @GetMapping("/{id}")
  public ResponseEntity<UserGetDto> getById(@PathVariable String id) {
    return ResponseEntity.ok(userService.getDtoById(id));
  }

  @Secured
  @PutMapping("/")
  public ResponseEntity<?> update(@AuthenticationPrincipal User user,
                                  @RequestBody UserUpdateDto dto) {
    userService.updateById(dto, user.getId());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Secured
  @GetMapping("/favorites")
  public ResponseEntity<List<AdGetListDto>> getFavoriteAdsByIdUserId(@AuthenticationPrincipal User user) {
    return ResponseEntity.ok(userService.getFavoriteAds(user));
  }

  @Secured
  @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> uploadAvatar(@AuthenticationPrincipal User user,
                                        @RequestParam MultipartFile image) {
    userService.setAvatar(image, user);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Secured
  @DeleteMapping("/")
  public ResponseEntity<?> delete(@AuthenticationPrincipal User user) {
    userService.deleteWithDependencies(user);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}

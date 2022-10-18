package com.vadmack.petter.user;

import com.vadmack.petter.ad.dto.AdGetDto;
import com.vadmack.petter.app.annotation.Secured;
import com.vadmack.petter.user.dto.UserCreateDto;
import com.vadmack.petter.user.dto.UserGetDto;
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

  @PostMapping
  public ResponseEntity<?> create(@RequestBody UserCreateDto dto) {
    userService.create(dto);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Secured
  @GetMapping
  public ResponseEntity<List<UserGetDto>> getAll() {
    return ResponseEntity.ok(userService.findAllDto());
  }

  @Secured
  @GetMapping("/{id}")
  public ResponseEntity<UserGetDto> getById(@PathVariable String id) {
    return ResponseEntity.ok(userService.findByIdDto(id));
  }

  @Secured
  @GetMapping("/favorites")
  public ResponseEntity<List<AdGetDto>> getFavoriteAdsByIdUserId(@AuthenticationPrincipal User user) {
    return ResponseEntity.ok(userService.getFavoriteAds(user));
  }

  @Secured
  @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> uploadImage(@AuthenticationPrincipal User user,
                                       @RequestParam MultipartFile image) {
    userService.setAvatar(image, user);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}

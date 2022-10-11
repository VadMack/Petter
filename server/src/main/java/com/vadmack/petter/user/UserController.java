package com.vadmack.petter.user;

import com.vadmack.petter.app.annotation.SecuredRestController;
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
public class UserController implements SecuredRestController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<?> create(@RequestBody UserCreateDto dto) {
    userService.create(dto);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<UserGetDto>> getAll() {
    return ResponseEntity.ok(userService.findAll());
  }

  @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> uploadImage(@AuthenticationPrincipal User user,
                                       @RequestParam MultipartFile image) {
    userService.addImage(image, user.getId().toString());
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
